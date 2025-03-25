package com.labs.ticketController.commands;

import java.util.Map;

import com.labs.common.Command;
import com.labs.common.exceptions.KeyNotFoundException;
import com.labs.ticketController.CollectionManager;

public class FilterGreaterThanRefundableCommand implements Command {
    private CollectionManager collectionManager;
    private Boolean refundable;

    public FilterGreaterThanRefundableCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Object execute() {
        return collectionManager.filterGreaterThanRefundable(refundable);
    }

    public void setArguments(Map<String, Object> data) throws KeyNotFoundException {
        if(!data.containsKey("refundable")) { throw new KeyNotFoundException("refundable"); }
        this.refundable = (Boolean)data.get("refundable");
    }
}