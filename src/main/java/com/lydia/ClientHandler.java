package com.lydia;

import java.lang.Runnable;
import java.net.Socket;
import java.io.*;

class ClientHandler implements Runnable {

    private BufferedReader reader;
    private DataOutputStream writer;
    private Socket clientSocket;
    private SocketIOStream sockStream;
    private Request request;
    private Response response;

    ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        System.out.println("New client handler thread");

        this.sockStream = new SocketIOStream(clientSocket);
        this.reader = sockStream.getReader();
        this.writer = sockStream.getWriter();

        // get input from client and store in request object
        this.request = RequestHandler.getRequest(new Request(), reader);
        RequestHandler.parseRequest(request);


        // send response as HTTP requires
        this.response = new Response();
        this.response.setStatusLine("HTTP/1.1 200 ROSIE");
        this.response.setBody(new String(request.getBody()));

        byte[] bytes = ResponseHandler.responseToBytes(this.response);
        ResponseHandler.sendResponse(bytes, writer);

        // close all streams and sockets
        TerminateConnections();
    }

    private  void TerminateConnections(){
        try{
            //System.out.println("closing client connections & ending thread");
            clientSocket.close();
            sockStream.closeStreams();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}