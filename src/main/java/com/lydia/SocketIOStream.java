package com.lydia;

import java.io.*;
import java.net.Socket;

public class SocketIOStream {

    private BufferedReader reader;
    private DataOutputStream writer;
    private Socket clientSocket;

    SocketIOStream(Socket socket){
        this.clientSocket = socket;
        // create and get input & output streams from socket if still open
        try{
            this.reader = createNewReader(clientSocket);
            this.writer = createNewWriter(clientSocket);
        }catch (IOException ex){
            //TODO handle closed connection et
            ex.printStackTrace();
        }
    }

    public BufferedReader getReader(){
        return reader;
    }

    public DataOutputStream getWriter(){return writer;}

    public void closeStreams() throws IOException{
        reader.close();
        writer.close();
    }

    private BufferedReader createNewReader(Socket socket) throws IOException {
        // Get (buffered char) input stream from socket
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private DataOutputStream createNewWriter(Socket socket) throws IOException{
        OutputStream output = socket.getOutputStream();
        return new DataOutputStream(output);
    }

}
