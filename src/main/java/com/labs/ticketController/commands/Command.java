package com.labs.ticketController.commands;

import java.util.Map;

import com.labs.common.Executable;
import com.labs.ticketController.exeptions.KeyNotFoundExeption;

public interface Command extends Executable{
    public void setArguments(Map<String, Object> data) throws KeyNotFoundExeption;
}