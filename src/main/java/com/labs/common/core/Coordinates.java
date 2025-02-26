package com.labs.common.core;

import java.io.Serializable;

import com.labs.utils.ValueChecker;

public class Coordinates implements Serializable {

    private final int xLimit = -47;
    private final float yLimit = -69;

    private int x; //Значение поля должно быть больше -47
    private float y; //Значение поля должно быть больше -69


    public Coordinates(Integer x, Float y) {
        try {
            setX(x);
            setY(y);
        } catch(IllegalArgumentException exception) {
            throw new IllegalArgumentException( this.getClass() + ": " + exception.getMessage(), exception);
        }
    }
    public Coordinates() {
        x = 0;
        y = 0;
    }


    public int x() {
        return x;
    }

    public double y() {
        return y;
    }

    
    public void setX(Integer x) {
        ValueChecker.checkLimits(x, xLimit, null, "X");
        this.x = x;
    }

    public void setY(Float y) {
        ValueChecker.checkLimits(y, yLimit, null, "Y");
        this.y = y;
    }
    @Override
    public String toString() {
        String result = "Coordinates ->\n";
        result += "X: " + String.valueOf(x) + "\n";
        result += "Y: " + String.valueOf(y) + "\n";
        return result;
    }
}