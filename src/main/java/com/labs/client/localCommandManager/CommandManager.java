package com.labs.client.localCommandManager;

import com.labs.client.Cycle;
import com.labs.client.DataManager;
import com.labs.client.FileManager;
import com.labs.common.DataContainer;

public class CommandManager {
    private Invoker invoker;
    public CommandManager(Cycle cycle, FileManager fileManager, DataManager dataManager) {
        invoker = new Invoker(cycle, fileManager, dataManager);
    }
    public DataContainer executeCommand(DataContainer dataContainer) {
        if(!invoker.check(dataContainer.getCommand())) return null;
        invoker.run(dataContainer);

        return invoker.getResponse();
    }
}
