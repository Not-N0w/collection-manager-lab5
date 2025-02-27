package com.labs.ticketController;

import java.io.IOException;


import com.labs.common.DataContainer;
import com.labs.common.dataConverter.Deserializer;
import com.labs.common.dataConverter.Serializer;
import com.labs.common.transmitter.Response;

public class TicketController {
    private Response response;
    private Invoker invoker;
    public TicketController() {
        response = Response.getInstance();
        invoker = new Invoker();
    }

    public void process(byte[] inData) {
        DataContainer data = null;
        DataContainer commandResponse = new DataContainer();
        try {
            data = Deserializer.deserialize(inData);
        }
        catch(IOException exception) {
            commandResponse.add("status", "error");
            commandResponse.add("message", "Deserialization error (IO).");
        }
        catch(ClassNotFoundException exception) {
            commandResponse.add("status", "error");
            commandResponse.add("message", "Deserialization error. Invalid class.");
        }

        invoker.run(data);
        commandResponse = invoker.getResponse();

        byte[] outData;
        try {
            outData = Serializer.serialize(commandResponse);
            response.setResponse(outData);
        } 
        catch(IOException exception) {
            //idk mb try again or sth else
        }
    }
}
