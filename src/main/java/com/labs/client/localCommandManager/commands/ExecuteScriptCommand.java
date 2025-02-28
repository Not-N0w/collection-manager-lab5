package com.labs.client.localCommandManager.commands;

import java.util.Map;
import com.labs.client.Cycle;
import com.labs.client.Input;
import com.labs.common.Command;
import com.labs.common.exeptions.KeyNotFoundExeption;

public class ExecuteScriptCommand implements Command {
    private String filePath;
    private Cycle cycle; 
    
    public ExecuteScriptCommand(Cycle cycle) {
        this.cycle = cycle;
    }

    @SuppressWarnings("unchecked")
    public Object execute() {
        Input input = new Input(cycle.output(), filePath);
        Cycle fileCycle = new Cycle(input, cycle.output(), cycle.fileManager(), cycle.dataManager());
        fileCycle.noComments();
        fileCycle.cycle();
        return null;
    }


    public void setArguments(Map<String, Object> data) throws KeyNotFoundExeption {
        if(!data.containsKey("path")) { throw new KeyNotFoundExeption("path"); }
        this.filePath = (String) data.get("path");
    }
}
