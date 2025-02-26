package com.labs.common.transmitter;

import com.labs.common.DataContainer;

public class Response {
    private static Response instance;
    public byte[] response;
    
    private Response() {}

    public static Response getInstance() {
        if(instance == null) {
            instance = new Response();
        }
        return instance;
    }

    public void setResponse(byte[] dc) {
        response = dc;
    }
    public byte[] getResponse() {
        return response;
    }
}
