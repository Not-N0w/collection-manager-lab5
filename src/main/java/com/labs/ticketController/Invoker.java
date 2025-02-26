package com.labs.ticketController;

import java.util.HashMap;
import java.util.Map;

import com.labs.common.DataContainer;
import com.labs.ticketController.commands.AddCommand;
import com.labs.ticketController.commands.Command;
import com.labs.ticketController.exeptions.KeyNotFoundExeption;

public class Invoker {
    private Map<String, Command> commands = new HashMap<>();
    private DataContainer response;
    public Invoker() {
        CollectionManager cm = new CollectionManager();
        commands.put("add", new AddCommand(cm));
    }
    public void run(DataContainer data) {
        String commandStr = data.getCommand();
        response = new DataContainer(commandStr);
        Command currentCommand = commands.get(commandStr);

        try {
            currentCommand.setArguments(data.fullGet());
        } 
        catch(KeyNotFoundExeption exception) {
            response.add("message", "Command failed: " + exception.getMessage());
            return;
        }

        try {
            response.add("data", currentCommand.execute());
            response.add("message", "Сommand executed successfully!");
        }
        catch(Exception exception) {
            response.add("message", "Command failed: " + exception.getMessage());
            return;
        }
    }
    public DataContainer getResponse() {
        return response;
    }
}
