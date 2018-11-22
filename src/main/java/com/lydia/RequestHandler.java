package com.lydia;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestHandler {

    // empty constructor
    RequestHandler(){}

    public static Request parseRequest(Request request, BufferedReader reader){

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
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // return req object with all req data parsed & set
        return request;
    }
}
