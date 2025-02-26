package com.labs.ticketController.commands;

import java.util.Map;

import com.labs.common.DataContainer;
import com.labs.ticketController.exeptions.KeyNotFoundExeption;

public interface Command {
    public DataContainer execute();
    public void setArguments(Map<String, Object> data) throws KeyNotFoundExeption;
}