package com.labs.ticketController.commands;

import java.util.Map;

import com.labs.ticketController.CollectionManager;
import com.labs.ticketController.exeptions.KeyNotFoundExeption;

public class ShowCommand implements Command {
    CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Object execute() {
        return collectionManager.getAll();
    }

    public void setArguments(Map<String, Object> data) throws KeyNotFoundExeption {
        //Just a structure method
    }
}