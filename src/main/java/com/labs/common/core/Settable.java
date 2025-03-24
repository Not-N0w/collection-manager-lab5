package com.labs.common.core;

@FunctionalInterface
public interface Settable {
    public <T> void set(String fieldName, T in) throws IllegalArgumentException;
}
