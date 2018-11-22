package com.lydia;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ResponseHandler {

    ResponseHandler(){}

    public static byte[] responseToBytes(Response response){
        // put response together with required elements
        byte[] statusLine = response.getStatusLine().getBytes();
        byte[] headers = response.getHeaders().getBytes();

        byte[] body = response.getBody().getBytes();

        byte[] bytesResponse = new byte[statusLine.length + headers.length + body.length];

        System.arraycopy(statusLine, 0, bytesResponse, 0, statusLine.length);
        System.arraycopy(headers, 0, bytesResponse, statusLine.length, headers.length);
        System.arraycopy(body, 0, bytesResponse, statusLine.length+headers.length, body.length);

        return bytesResponse;
    }

    public static byte[] sendResponse(byte[] response, DataOutputStream writer){
        try{
            //write the response to the output stream - client will be listening for this
            writer.write(response);
        }catch (IOException ex){
            // TODO: handle failure to send response - maybe try 3 times or until timeout?
            ex.printStackTrace();
        }
        return response;
    }

}
