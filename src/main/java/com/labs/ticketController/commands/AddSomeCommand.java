package com.labs.ticketController.commands;

import java.util.ArrayList;
import java.util.Map;

import com.labs.common.core.Ticket;
import com.labs.ticketController.CollectionManager;
import com.labs.ticketController.exeptions.KeyNotFoundExeption;

public class AddSomeCommand implements Command {
    ArrayList<Ticket> tickets;
    CollectionManager collectionManager;
    public AddSomeCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Object execute() {
        for(var ticket : tickets) {
            collectionManager.add(ticket);
        }
        return null;
    }

    public void setArguments(Map<String, Object> data) throws KeyNotFoundExeption {
        if(!data.containsKey("tickets")) { throw new KeyNotFoundExeption("tickets"); }
        this.tickets = (ArrayList<Ticket>)data.get("tickets");
    }
}
