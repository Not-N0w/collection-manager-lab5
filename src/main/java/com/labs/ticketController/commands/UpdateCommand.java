package com.labs.ticketController.commands;

import java.util.Map;

import com.labs.common.Command;
import com.labs.common.DataContainer;
import com.labs.common.core.Ticket;
import com.labs.common.exeptions.KeyNotFoundExeption;
import com.labs.ticketController.CollectionManager;

public class UpdateCommand implements Command {
    private Ticket ticket;
    private Long id;
    CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Object execute() {
        collectionManager.update(id, ticket);
        return null;
    }


    @Override
    public void setArguments(Map<String, Object> data) throws KeyNotFoundExeption {
        if(!data.containsKey("ticket")) { throw new KeyNotFoundExeption("ticket"); }
        this.ticket = (Ticket)data.get("ticket");

        if(!data.containsKey("id")) { throw new KeyNotFoundExeption("id"); }
        this.id = (Long)data.get("id");
    }
}
