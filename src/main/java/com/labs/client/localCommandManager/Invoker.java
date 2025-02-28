package com.labs.client.localCommandManager;

import com.labs.client.Cycle;
import com.labs.client.DataManager;
import com.labs.client.FileManager;
import com.labs.client.localCommandManager.commands.ExecuteScriptCommand;
import com.labs.client.localCommandManager.commands.ExitCommand;
import com.labs.client.localCommandManager.commands.HelpCommand;
import com.labs.client.localCommandManager.commands.SaveCommand;
import com.labs.common.AbstractInvoker;


public class Invoker extends AbstractInvoker {
    public Invoker(Cycle cycle, FileManager fileManager, DataManager dataManager) {
        commands.put("help", new HelpCommand());
        commands.put("exit", new ExitCommand());
        commands.put("save", new SaveCommand(fileManager, dataManager));
        commands.put("execute_script", new ExecuteScriptCommand(cycle));
    }

    public boolean check(String command) {
        return commands.containsKey(command);
    }
}
