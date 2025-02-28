package com.labs.client.localCommandManager.commands;

import com.labs.common.Command;


public class ExitCommand implements Command {
    public Object execute() {
        System.exit(0);
        return null;
    }
}
