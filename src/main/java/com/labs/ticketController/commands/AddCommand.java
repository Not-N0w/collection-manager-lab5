package com.labs.ticketController.commands;

import java.util.Map;

import com.labs.common.Command;
import com.labs.common.core.Ticket;
import com.labs.common.exeptions.KeyNotFoundExeption;
import com.labs.ticketController.CollectionManager;

public class AddCommand implements Command {
    private Ticket ticket;
    CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Object execute() {
        collectionManager.add(ticket);
        return null;
    }

    public void setArguments(Map<String, Object> data) throws KeyNotFoundExeption {
        if(!data.containsKey("ticket")) { throw new KeyNotFoundExeption("ticket"); }
        this.ticket = (Ticket)data.get("ticket");
    }
}
