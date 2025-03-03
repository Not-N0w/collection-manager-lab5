package com.labs.client.localCommandManager.commands;

import com.labs.client.Cycle;
import com.labs.common.Command;


public class ExitCommand implements Command {
    private Cycle cycle; 
    
    public ExitCommand(Cycle cycle) {
        this.cycle = cycle;
    }

    public Object execute() {
        cycle.leave();
        return null;
    }
}
