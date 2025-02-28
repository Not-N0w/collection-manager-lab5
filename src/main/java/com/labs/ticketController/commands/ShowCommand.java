package com.labs.ticketController.commands;

import com.labs.common.Command;
import com.labs.ticketController.CollectionManager;

public class ShowCommand implements Command {
    CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Object execute() {
        return collectionManager.getAll();
    }
}