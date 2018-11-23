package com.lydia;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestHandler {

    // empty constructor
    RequestHandler(){}

    public static Request getRequest(Request request, BufferedReader reader){

        //get request details
        try {
            // set requestline in req obj
            request.setRequestType(reader.readLine());

            // store all headers in request object
            while (true) {
                String line = reader.readLine();
                //loop until we find empty line to indicate end of headers
                if (line == null || line.isEmpty()) {
                    break;
                }
                line=line.trim();
//                System.out.println("addnig header:... "+line);
                request.setHeader(line);
            }

            // set body in request object if it exists (there will be content-length header if there is a body)
            if (request.hasHeader("Content-Length")) {

                //get content length so we know how many chars to read
                int contentLength = Integer.parseInt(request.getHeaderValue("Content-Length"));
                char[] body = new char[contentLength];
                //char[] body = new char[];
                reader.read(body, 0, contentLength);
                request.setBody(body);
              //  System.out.println("adding body:... "+new String(request.getBody()));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // return req object with all req data parsed & set
        return request;
    }

    public static Request parseRequest(Request request) {
        //first check request type: GET/POST/etc
        if (request.getMethod().equals("GET")) {
            //TODO check path/resources required etc - probably want to return graphing page

        } else if (request.getMethod().equals("POST")) {
            // process body
            //System.out.println("parsing request body..." + new String(request.getBody()));
            MessageHandler.processPost(new String(request.getBody()));
        }
        return request;
    }

}
