package com.lydia;

import java.util.Hashtable;
import java.util.Map;

public class Request {

    private Map<String, String> headers;
    private String method = null;
    private String path;
    private String protocol;
    private char[] body;

    public Request() {

        headers = new Hashtable<String, String>();
    }

    // get and set body methods
    public char[] getBody() {
        return body;
    }

    public Request setBody(char[] body) {
        this.body = body;
        return this;
    }

    public boolean hasHeader(String headerName) {
        return headers.containsKey(headerName);
    }

    public String getHeaderValue(String headerName) {
        return headers.get(headerName);
    }

    public Request setHeader(String header) {
        String[] keyAndValue = header.split(": ");
        String key = keyAndValue[0];
        String value = keyAndValue[1];
        headers.put(key, value);

        return this;
    }

    public String getMethod() {
        return method;
    }

    public String getProtocol() {
        return protocol;
    }

    public Request setRequestType(String requestLine) {

        String[] parts = requestLine.split(" ");
        method = parts[0];
        path = parts[1];
        protocol = parts[2];

        return this;
    }
}
