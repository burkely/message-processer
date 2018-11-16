package com.lydia;

import java.lang.Runnable;
import java.net.Socket;
import java.io.*;

class ClientHandler implements Runnable {

    private static BufferedReader reader;
    private static DataOutputStream writer;
    private static Socket clientSocket;

    ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        System.out.println("running a new thread");
        // create and get input & output streams from socket
        this.reader = createNewReader(clientSocket);
        this.writer = createNewWriter(clientSocket);

        // wait for input from client
        Request request = getRequest();

        // send response as per HTTP
        sendResponse(request);

        // close all streams and sockets
        TerminateConnections();
    }

    private static BufferedReader createNewReader(Socket socket){
        try{
            BufferedReader reader;
            // Get (buffered character) input stream from socket
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return reader;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static DataOutputStream createNewWriter(Socket socket){
        try{
            // if socket still open get an output stream to write response to later
            OutputStream output = socket.getOutputStream();
            writer = new DataOutputStream(output);
            return writer;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static Request getRequest(){
        Request request = new Request();

        //get request details
        try {
            // set request type
            request.setRequestType(reader.readLine());
            // store all headers in request object
            while (true) {
                String line = reader.readLine().trim();
                //loop until we find empty line to indicate end of headers
                if (line == null || line.isEmpty()) {
                    break;
                }
                request.setHeader(line);
            }

            // set body in request object if it exists (there will be content-length header if there is a body)
            if (request.hasHeader("Content-Length")) {
                //get content length so we know how many chars to read
                int contentLength = Integer.parseInt(request.getHeaderValue("Content-Length"));
                char[] body = new char[contentLength];
                reader.read(body, 0, contentLength);
                request.setBody(body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return request;
    }

    private static void sendResponse(Request request){
        // create byte arrays for headers & body
        byte[] header = ("HTTP/1.1 200 OK"+"\n"+"\n").getBytes();
        byte[] body = ("Good Morning Vietnam").getBytes();
        //create new response array to hold headers & body
        byte[] response = new byte[header.length + body.length];
        //copy contents of header and body arrays to response array
        System.arraycopy(header, 0, response, 0, header.length);
        System.arraycopy(body, 0, response, header.length, body.length);

        try{
            //write the response to the output stream - client will be listening for this
            writer.write(response);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private static void TerminateConnections(){
        try{
            System.out.println("closing client connections & ending thread");
            clientSocket.close();
            reader.close();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}