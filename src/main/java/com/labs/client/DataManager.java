package com.labs.client;

import java.io.IOException;

import com.labs.client.extra.Pair;
import com.labs.common.DataContainer;
import com.labs.common.dataConverter.Deserializer;
import com.labs.common.dataConverter.Serializer;
import com.labs.common.transmitter.Request;
import com.labs.common.transmitter.Response;

public class DataManager {
    private Request request;
    private Response response;
    private Output output;


    public DataManager(Output output) {
        request = new Request();
        response = Response.getInstance();
        this.output = output;
    }
    public boolean send(DataContainer commandData) {
        byte[] serialized;
        try {
            serialized = Serializer.serialize(commandData);
            request.request(serialized);
        } 
        catch(IOException exception) {
            output.outError("Serialization error");
            output.outError("Try again\n");
            return false;
        }
        return true;
    }

    public boolean sendCommand(String command, Pair<String, Object> ... pairs) {
        DataContainer dataContainer = new DataContainer(command);
        for(var item : pairs) {
            dataContainer.add(item.key(), item.value());
        }
        return send(dataContainer);
    }

    public DataContainer getResponse() {
        DataContainer commandResponse;
        try {
            commandResponse = Deserializer.deserialize(response.getResponse());
            return commandResponse;
        }
        catch(IOException exception) {
            output.outError("Response derialization error (IO).");
            output.outError("Try again\n");
            return null;
        }
        catch(ClassNotFoundException exception) {
            output.outError("Response derialization error. Invalid class.");
            output.outError("Try again\n");
            return null;
        }
    }

    public void processResponse() {
        DataContainer commandResponse;
        try {
            commandResponse = Deserializer.deserialize(response.getResponse());
            output.responseOut(commandResponse);
        }
        catch(IOException exception) {
            output.outError("Response derialization error (IO).");
            output.outError("Try again\n");
        }
        catch(ClassNotFoundException exception) {
            output.outError("Response derialization error. Invalid class.");
            output.outError("Try again\n");
        }
    }
}
