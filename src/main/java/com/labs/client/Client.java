package com.labs.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.labs.common.DataContainer;
import com.labs.common.core.Ticket;
import com.labs.common.dataConverter.Deserializer;
import com.labs.common.dataConverter.Serializer;
import com.labs.common.transmitter.Request;
import com.labs.common.transmitter.Response;

public class Client {
    private Input input;
    private Output output;
    private Request request;
    private Response response;
    private FileManager fileManager;

    public Client(String filePath) {
        input = new Input();
        output = new Output();
        request = new Request();
        response = Response.getInstance();
        fileManager = new FileManager(input, output, filePath);
    }
    public Client() {
        this("");
    }
    private Boolean send(DataContainer commandData) {
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

    private DataContainer getCommandData() {
        String command;
        DataContainer commandData = null;
        try {
            command = input.getCommand();
            commandData = input.getData(command);
        } 
        catch(IllegalArgumentException exception) {
            output.outError(exception.getMessage());
            output.outError("Try again\n");
        }
        return commandData;
    }

    private DataContainer processResponse() {
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
    private void save(DataContainer commandData) {
        DataContainer dc = new DataContainer("show");
        if(!send(dc)) return;
        DataContainer response = processResponse();
        fileManager.saveTickets((ArrayList<Ticket>)response.get("data"));
        output.outOk("Saved succsessfully!");
    }
    private void help(DataContainer commandData) {
        String helpInfo = fileManager.getRecoursesFileData("help.txt");
        output.out(helpInfo);
    }

    private Boolean checkLocalCommands(DataContainer commandData) {
        String command = commandData.getCommand();
        if(command.equals("save")) {
            save(commandData);
            return true;
        }
        else if(command.equals("help")) {
            help(commandData);
            return true;
        }
        else if(command.equals("exit")) {
            System.exit(0);
        }
        else if(command.equals("execute_script")) {
            Input saved = input;
            try {
                String path = ((String)commandData.get("path")).strip();
                input = new Input(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            commandCycle();
            input = saved;
            return true;
        }
        return false;
    }
    private void commandCycle() {
        while(true) {
            output.outSub("Waiting for commands");
            DataContainer commandData = getCommandData();
            if(commandData == null) continue;
            if(checkLocalCommands(commandData)) continue;
            if(!send(commandData)) continue;
            DataContainer response = processResponse();
            if(response != null) output.responseOut(response);
        }
    }

    public void run() {
        fileManager.makeValidCollectionFile();
        try {
            ArrayList<Ticket> tickets = fileManager.getTickets();
            DataContainer dataContainer = new DataContainer("addSome");
            dataContainer.add("tickets", tickets);
            send(dataContainer);
            DataContainer response = processResponse();
            if(response != null) output.responseOut(response);
        }
        catch(Exception exception) {
            output.outError(exception.getMessage());
        }
        commandCycle();
    }
}

/*
 * string to a number value = gg
 * 
 * 
 */