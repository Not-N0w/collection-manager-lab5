package com.labs.ticketController.exeptions;

public class KeyNotFoundExeption extends Exception {
    public KeyNotFoundExeption(String key) {
        super("Key " + key + " not found.");
    }
}
