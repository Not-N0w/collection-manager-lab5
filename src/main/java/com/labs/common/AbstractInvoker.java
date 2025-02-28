package com.labs.common;

import java.util.HashMap;
import java.util.Map;

import com.labs.common.exeptions.KeyNotFoundExeption;


public abstract class AbstractInvoker {
    protected Map<String, Command> commands = new HashMap<>();
    private DataContainer response;
    public void run(DataContainer data) {
        String commandStr = data.getCommand();
        response = new DataContainer(commandStr);
        Command currentCommand = commands.get(commandStr);

        try {
            currentCommand.setArguments(data.fullGet());
        } 
        catch(KeyNotFoundExeption exception) {
            response.add("status", "error");
            response.add("message", "Command failed: " + exception.getMessage());
            return;
        }

        try {
            response.add("data", currentCommand.execute());
            response.add("status", "ok");
            response.add("message", "Сommand executed successfully!");
        }
        catch(Exception exception) {
            response.add("status", "error");
            response.add("message", "Command failed: " + exception.getMessage());
            return;
        }
    }
    public DataContainer getResponse() {
        return response;
    }
}
