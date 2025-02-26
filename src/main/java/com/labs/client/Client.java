package com.labs.client;

import java.io.IOException;

import com.labs.common.DataContainer;
import com.labs.common.dataConverter.Deserializer;
import com.labs.common.dataConverter.Serializer;
import com.labs.common.transmitter.Request;
import com.labs.common.transmitter.Response;

public class Client {
    private Input input;
    private Output output;
    private Request request;
    private Response response;

    public Client(String filePath) {
        input = new Input(filePath);
        output = new Output();
        request = new Request();
        response = Response.getInstance();
    }
    public Client() {
        this("");
    }

    private void commandCycle() {
        while(true) {
            output.outSub("Waiting for commands");
            String command;
            DataContainer commandData = null;
            try {
                command = input.getCommand();
                commandData = input.getData(command);
            } 
            catch(IllegalArgumentException exception) {
                output.outError(exception.getMessage());
                output.outError("Try again\n");
                continue;
            }
            byte[] serialized;
            try {
                serialized = Serializer.serialize(commandData);
                request.request(serialized);
            } 
            catch(IOException exception) {
                output.outError("Serialization error");
                output.outError("Try again\n");
                continue;
            }
            //getResponse
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
    private String makeValidCollectionFile() {
        String filePath = "";
        try {
            filePath = input.makeCollectionFile();
        } 
        catch(Exception exception) {
            output.fileNotExistMessage(exception.getMessage());
            filePath = makeValidCollectionFile();
        }
        return filePath;
    }

    public void run() {
        String filePath = makeValidCollectionFile();
        try {
            String fileData = input.getCollectionFileData();
            //ticketController.addJSONData(fileData);
            output.successfulFileDataLoaded(filePath);
        }
        catch(Exception exception) {
            output.outError(exception.getMessage());
        }
        commandCycle();
    }
}