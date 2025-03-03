package com.labs.common.core;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.labs.client.ValueChecker;

public final class Ticket implements Serializable, Comparable<Ticket> {
    static Long nextID = Long.valueOf(1);

    private final int priceLimit = 0;

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int price; //Значение поля должно быть больше 0
    private Boolean refundable; //Поле не может быть null
    private TicketType type; //Поле не может быть null
    private Person person; //Поле может быть null
    
    public Ticket(String name, Coordinates coordinates, Integer price, Boolean refundable, TicketType type, Person person) {
        super();
        try {
            setName(name);
            setCoordinates(coordinates);
            setPrice(price);
            setRfundable(refundable);
            setType(type);
            setPerson(person);
        } 
        catch(IllegalArgumentException exception) {
            throw new IllegalArgumentException( this.getClass() + ": " + exception.getMessage(), exception);
        }
    }
    public Ticket() {
        this.id = nextID++;
        this.creationDate = LocalDateTime.now();
    }


    public Long id() {
        return this.id;
    }
    public String name() {
        return this.name;
    }
    public Coordinates coordinates() {
        return this.coordinates;
    }
    public java.time.LocalDateTime creationDate() {
        return this.creationDate;
    }
    public int price() {
        return this.price;
    }
    public Boolean refundable() {
        return this.refundable;
    }
    public TicketType type() {
        return this.type;
    }
    public Person person() {
        return this.person;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        ValueChecker.nullCheck(name, "Name");
        ValueChecker.stringEmptyCheck(name, "Name");
        this.name = name;
    }
    public void setCoordinates(Coordinates coordinates) {
        ValueChecker.nullCheck(coordinates, "Coordinates");
        this.coordinates = coordinates;
    }
    public void setPrice(Integer price) {
        ValueChecker.checkLimits(price, priceLimit, null, "Price");
        this.price = price;
    }
    public void setRfundable(Boolean refundable) {
        ValueChecker.nullCheck(refundable, "Refundable");
        this.refundable = refundable;
    }
    public void setType(TicketType type) {
        ValueChecker.nullCheck(type, "Type");
        this.type = type;
    }
    public void setPerson(Person person) {
        ValueChecker.nullCheck(person, "Person");
        this.person = person;
    }
    private String tab(Object obj) {
        String[] objStrings = obj.toString().split("\n");
        String result = "";
        for (String str : objStrings) {
            result += "    " + str + "\n";
        }
        return result;   
    }

    @Override
    public String toString() {
        String result = "Ticket ->\n";
        result += "    Name: " + name + "\n";
        result += "    ID: " + String.valueOf(id) + "\n";
        result += tab(coordinates.toString());
        result += "    CreationDate: "+ creationDate.toString() + "\n";
        result += "    Price: " + String.valueOf(price) + "\n";
        result += "    Refundable: " + String.valueOf(refundable) + "\n";
        result += "    TicketType: " + type.name() + "\n";
        result += tab(person.toString());
        return result;
    }
    
    @Override
    public int compareTo(Ticket other) {
        if(this.refundable.compareTo(other.refundable) == 0) {
            if(this.price == other.price) {
                return this.id.compareTo(other.id);
            }
            else if(this.price < other.price) {
                return -1;
            }
            else {
                return 1;
            }
        }
        else if(this.refundable.compareTo(other.refundable) < 0) {
            return -1;
        }
        else {
            return 1;
        }
    }

}