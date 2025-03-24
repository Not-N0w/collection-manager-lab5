package com.labs.ticketController.commands;

import java.util.ArrayList;
import java.util.Map;

import com.labs.common.Command;
import com.labs.common.core.Ticket;
import com.labs.common.exeptions.KeyNotFoundExeption;
import com.labs.ticketController.CollectionManager;

public class AddSomeCommand implements Command {
    private ArrayList<Ticket> tickets;
    private CollectionManager collectionManager;
    public AddSomeCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Object execute() {
        for(var ticket : tickets) {
            collectionManager.add(ticket);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void setArguments(Map<String, Object> data) throws KeyNotFoundExeption {
        if(!data.containsKey("tickets")) { throw new KeyNotFoundExeption("tickets"); }
        this.tickets = (ArrayList<Ticket>)data.get("tickets");
    }
}
