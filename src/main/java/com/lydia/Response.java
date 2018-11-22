package com.lydia;

import java.util.Hashtable;
import java.util.Map;

/**
 * HTTP Response Blueprint:
 *   -  A Status-line: http version, status code, reason/phrase
 *   -  Zero or more header (General|Response|Entity) fields followed by CRLF
 *   -  An empty line (i.e., a line with nothing preceding the CRLF)
 *        indicating the end of the header fields
 *   -  Optional message-body
 */

public class Response {


    private String statusLine;
    private String httpVersion;
    private String statusCode;
    private String reason;
    private static final String CRLF = "\r\n";
    private Map<String, String> headers;
    private String body;

    public Response() {
        // init headers in constructor to ensure we have map object to work with later
        // without having to check it exists each time
        this.headers = new Hashtable<String, String>();
    }

    /**
     **  getter and setter methods
     **/

    public String getStatusLine() {
        return statusLine;
    }

    public Response setStatusLine(String line) {
        this.statusLine = line+CRLF;

        // also set individual parts of response
        String[] parts = line.split(" ");
        this.httpVersion = parts[0];
        this.statusCode = parts[1];
        this.reason = parts[2];

        return this;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public Response setStatusCode(String code) {
        this.statusCode = code;
        return this;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public Response setHttpVersion(String version) {
        this.httpVersion = version;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public Response setReason(String phrase) {
        this.statusLine = phrase;
        return this;
    }


    public String getBody() {
        return body;
    }

    public Response setBody(String body) {
        this.body = body;
        return this;
    }


    public String getHeaders(){

        String headerLines = "";

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            System.out.println("in for loop");
            String key = entry.getKey();
            String value = entry.getValue();
            headerLines = headerLines + String.format("%s: %s", key, value)+CRLF;
        }



        System.out.println("get headerlines: " + headerLines+CRLF);
        return (headerLines+CRLF);
    }

    public Response setHeader(String header) {
        // split string into header name and value and store two parts in array
        String[] nameAndValue = header.split(": ");
        // add name & value to hashmap using header name as key
        headers.put(nameAndValue[0], nameAndValue[1]);
        return this;
    }



}
