package com.lydia;

import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.Assert.assertEquals;


public class ProcessorTest {

    private BufferedReader in;
    private Writer out;
    private int count = 0;
    private String expectedResponse = "HTTP/1.1 200 OK"+"\r\n\r\n"+"Good Morning Vietnam";
    private Response response = new Response();

    @Test
    public void testHandlerThread() throws IOException{
        System.out.println("testing...");
        while (count<=10){
            Socket clientSock = new Socket("localhost", 8080);
            out = new PrintWriter(clientSock.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
            out.write("GET / HTTP1.1\n\r");
            out.flush();

            //set status line of response
            response.setStatusLine(in.readLine());

            //set headers
            while(true){
                String line = in.readLine();
                //loop until we find empty line to indicate end of headers
                if (line == null || line.isEmpty()) {
                    break;
                }
                line = line.trim();
                response.setHeader(line);
            }

            //set Body
            String body = in.readLine();
            //check if there is a body:
            if (!(body == null || body.isEmpty())) {
                response.setBody(body);
            }

            String responseString = "";
            responseString += response.getStatusLine()+response.getHeaders()+response.getBody();

            assertEquals(expectedResponse, responseString);
            count++;
        }



    }
}