package com.labs.common;

import java.util.Map;

import com.labs.common.exeptions.KeyNotFoundExeption;

public interface Command extends Executable{
    public default void setArguments(Map<String, Object> data) throws KeyNotFoundExeption {
        //do nothing
    }
}