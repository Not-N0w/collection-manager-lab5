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
    private boolean needLeave = false;

    public Cycle(Input input, Output output, FileManager fileManager, DataManager dataManager) {
        localCommandManager = new CommandManager(this, fileManager, dataManager);
        this.input = input;
        this.output = output;
        this.fileManager = fileManager;
        this.dataManager = dataManager;
    }

    public void leave() {
        needLeave = true;
    }


    public void cycle() {
        while(!needLeave) {
            output.waiting();
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
                output.responseOut(localResponse);
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
