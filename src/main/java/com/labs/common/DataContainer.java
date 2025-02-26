package com.labs.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class DataContainer implements Serializable {
    private Map<String, Object> data;
    private String command;
    private static final long serialVersionUID = 1L;

    public DataContainer() {
        data =  new HashMap<>();
    }
    public DataContainer(String command) {
        this();
        this.command = command;
    }

    public void add(String key, Object value) {
        data.put(key, value);
    }
    public <T> T get(String key) {
        try {
            T value = (T)data.get(key);
            return value;
        }
        catch(ClassCastException exception) {
            return null;
        }
    }
    public Map<String, Object> fullGet() {
        return this.data;
    }
    public void setCommad(String command) {
        this.command = command;
    }
    public String getCommand() {
        return command;
    }


}
