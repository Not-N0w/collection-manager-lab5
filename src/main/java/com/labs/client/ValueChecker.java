package com.labs.client;

public class ValueChecker {

    private ValueChecker() {}

    public static <T extends Number & Comparable<T>> void checkLimits(T item, T limitDown, T limitUp, String varName) {
        String message = varName;
        nullCheck(item, varName);

        if (limitDown == null && limitUp != null) {
            if (item.compareTo(limitUp) >= 0) { // item >= limitUp
                message += " should be less than " + String.valueOf(limitUp);
                throw new IllegalArgumentException(message);
            }
        } else if (limitDown != null && limitUp == null) {
            if (item.compareTo(limitDown) <= 0) { // item <= limitDown
                message += " should be more than " + String.valueOf(limitDown);
                throw new IllegalArgumentException(message);
            }
        } else if (limitDown != null && limitUp != null) {
            if (item.compareTo(limitDown) <= 0 || item.compareTo(limitUp) >= 0) { // item <= limitDown || item >= limitUp
                message += " should be less than " + String.valueOf(limitUp) + "and more than " + String.valueOf(limitDown);
                throw new IllegalArgumentException(message);
            }
        }
    }

    public static <T> void nullCheck(T item, String varName) {
        if(item == null) {
            String message = varName + " value can't be null!";
            throw new IllegalArgumentException(message);
        }
    }

    public static void stringEmptyCheck(String item, String varName) {
        if(item.isEmpty()) {
            String message = varName + " value can't be null!";
            throw new IllegalArgumentException(message);
        }
    }
}