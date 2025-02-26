package com.labs.common.core;

import java.io.Serializable;

import com.labs.utils.ValueChecker;

public class Location implements Serializable {

    private Float x; //Поле не может быть null
    private Float y; //Поле не может быть null
    private Long z; //Поле не может быть null


    public Location(Float x, Float y, Long z) {
        try {
            setX(x);
            setY(y);
            setZ(z);
        } 
        catch(IllegalArgumentException exception) {
            throw new IllegalArgumentException( this.getClass() + ": " + exception.getMessage(), exception);
        }
    }
    public Location() {}

    public Float x() {
        return this.x;
    }
    public Float y() {
        return this.y;
    }
    public Long z() {
        return this.z;
    }

    public void setX(Float x) {
        ValueChecker.nullCheck(x, "X");
        this.x = x;
    }

    public void setY(Float y) {
        ValueChecker.nullCheck(y, "Y");
        this.y = y;
    }

    public void setZ(Long z) {
        ValueChecker.nullCheck(z, "Z");
        this.z = z;
    }
    @Override
    public String toString() {
        String result = "Location ->\n";
        result += "X: " + String.valueOf(x) + "\n";
        result += "Y: " + String.valueOf(y) + "\n";
        result += "Z: " + String.valueOf(z) + "\n";
        return result;
    }
}