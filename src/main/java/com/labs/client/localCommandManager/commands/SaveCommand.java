package com.labs.client.localCommandManager.commands;

import java.util.ArrayList;

import com.labs.client.DataManager;
import com.labs.client.FileManager;
import com.labs.common.Command;
import com.labs.common.DataContainer;
import com.labs.common.core.Ticket;

public class SaveCommand implements Command  {
    public FileManager fileManager;
    public DataManager dataManager;
    public SaveCommand(FileManager fileManager, DataManager dataManager) {
        this.fileManager = fileManager;
        this.dataManager = dataManager;
    }

    @SuppressWarnings("unchecked")
    public Object execute()  {
        dataManager.sendCommand("show");
        DataContainer response = dataManager.getResponse();
        fileManager.saveTickets((ArrayList<Ticket>)response.get("data"));
        return "Saved succsessfully!";
    }
}