package com.labs.client;

import java.util.NoSuchElementException;

import com.labs.client.localCommandManager.CommandManager;
import com.labs.common.DataContainer;

public class Cycle {
    private CommandManager localCommandManager;
    private Input input;
    private Output output;
    private FileManager fileManager;
    private DataManager dataManager;
    private boolean isFileReading;
    public Cycle(Input input, Output output, FileManager fileManager, DataManager dataManager) {
        localCommandManager = new CommandManager(this, fileManager, dataManager);
        this.input = input;
        this.output = output;
        this.fileManager = fileManager;
        this.dataManager = dataManager;
    }
    public void noComments() {
        input.noComments();
        this.isFileReading = true;
    }
    public void allowComments() {
        input.allowComments();
        this.isFileReading = false;
    }

    public void cycle() {
        while(true) {
            if(!isFileReading) output.outWaiting();
            String command = "";
            try {
                command = input.getCommand();
            }
            catch(NoSuchElementException exception) {
                return;
            }

            DataContainer commandData = input.getData(command);
            if(commandData == null) continue;

            DataContainer localResponse = localCommandManager.executeCommand(commandData);
            if(localResponse != null) {
                if(!isFileReading) output.responseOut(localResponse);
                continue;
            } 

            dataManager.send(commandData);
            dataManager.processResponse();
        }
    }
    public FileManager fileManager() {
        return this.fileManager;
    } 
    public DataManager dataManager() {
        return this.dataManager;
    }
    public Input input() {
        return this.input;
    }
    public Output output() {
        return this.output;
    }
    
}
