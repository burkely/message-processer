package com.lydia;

/**
 * Created by Lydia Burke Nov 16 18
 */

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Processor {

    //limit the number of threads to ensure system can sustain overhead required to run threads
    private static ExecutorService executor = Executors.newFixedThreadPool(50);
    private static final int PORT_NUMBER = 8080;
    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        // set up and bind server socket
        createServerSocket();

        // start function that waits for incoming connection and starts
        // new thread for each connection (to handle multiple clients)
        while (true) {
            startHandlerThread();
        }
    }

    protected void finalize() throws IOException {
        // close server socket connection on cleanup so port remains available
        serverSocket.close();
    }

    private static void createServerSocket() throws IOException {
        try {
            // create new server socket object & bind to port
            serverSocket = new ServerSocket(PORT_NUMBER);
        } catch (BindException ex) {
            // couldn't connect socket object to port
            serverConnectionFailed();
        }
    }

    private static void serverConnectionFailed() throws IOException {
        // advise connection failed, port already in use most likely
        System.out.println("Cannot connect to port: "+String.valueOf(PORT_NUMBER)+", system exiting.");
        // terminate program
        System.exit(-1);
    }

    static void startHandlerThread() throws IOException {
        // create new clienthandler for when there's an incoming connection
        ClientHandler clientHandler = new ClientHandler(serverSocket.accept());
        // start thread to handle this connection
        executor.execute(clientHandler);
    }


}

