package com.labs.client;

import java.time.LocalDate;
import java.util.Scanner;


import com.labs.common.DataContainer;
import com.labs.common.core.Coordinates;
import com.labs.common.core.Location;
import com.labs.common.core.Person;
import com.labs.common.core.Ticket;
import com.labs.common.core.TicketType;

public class CommandDataParser {
    private Scanner scanner;
    private boolean isFileReading = false;

    public CommandDataParser(Scanner scanner) {
        this.scanner = scanner;
    }
    public void noComments() {
        isFileReading = true;
    }
    public void allowComments() {
        isFileReading = false;
    }

    public <T> T scannerGet(String varName, Class<T> type) {
        try {
            String input = scanner.nextLine().strip();
            if(input.equals("")) {
                return null;
            }

            return switch (type.getSimpleName()) {
                case "Double" -> type.cast(Double.parseDouble(input));
                case "Float" -> type.cast(Float.parseFloat(input));
                case "Integer" -> type.cast(Integer.parseInt(input));
                case "Long" -> type.cast(Long.parseLong(input));
                case "Boolean" -> type.cast(Boolean.parseBoolean(input));
                case "String" -> type.cast(input.replaceAll("\n", ""));
                default -> throw new IllegalArgumentException("Unsupported type: " + type);
            };
        } catch (Exception exception) {
            //scanner.next();
            throw new IllegalArgumentException(varName + " should be " + type.getSimpleName() + "!", exception);
        }
    }

    private Coordinates parseCoordinates(int tabs) {
        Coordinates coordinates = new Coordinates();

        fieldOut(tabs, "Coordinates->\n");
        tabs += 1;

        fieldOut(tabs, "X: ");

        Integer scanerXData = scannerGet("X", Integer.class);
        coordinates.setX(scanerXData);

        fieldOut(tabs, "Y: ");
        coordinates.setY(scannerGet("Y", Float.class));
        return coordinates;
    }

    private LocalDate dateParse(String dateString) {
        LocalDate localDate = LocalDate.parse(dateString);
        return localDate;
    }

    private Location parseLocation(int tabs) {
        Location location = new Location();

        fieldOut(tabs, "Location->\n");

        tabs += 1;
        fieldOut(tabs, "X: ");
        location.setX(scannerGet("X", Float.class));

        fieldOut(tabs, "Y: ");
        location.setY(scannerGet("Y", Float.class));

        fieldOut(tabs, "Z: ");
        location.setZ(scannerGet("Z", Long.class));

        return location;
    }

    private Person parsePerson(int tabs) {
        Person person = new Person();
        fieldOut(tabs, "Person->\n");
        tabs += 1;
        scanner.nextLine();

        fieldOut(tabs, "Birthday (YYYY-MM-DD): ");
        String date = scannerGet("Birthday", String.class);
        try {
            person.setBirthday(dateParse(date));
        } 
        catch(Exception exception) {
            throw new IllegalArgumentException("Date should be in format YYYY-MM-DD!", exception);
        }        

        fieldOut(tabs, "Weight: ");
        person.setWeight(scannerGet("Weight", Double.class));
        
        fieldOut(tabs, "PassportID: ");
        person.setPassportID(scannerGet("PassportID", String.class));

        person.setLocation(parseLocation(tabs));
        
        return person;
    }

    private void fieldOut(int tabs, String in) {
        if(isFileReading) return;
        System.out.print("    ".repeat(tabs) + in);
    }

    private Ticket skipParseTicket() {
        scanner.nextLine();
        return parseTicket();
    }
    private Ticket parseTicket() {
        Ticket ticket = new Ticket();
        int tabs = 0;

        fieldOut(tabs, "Ticket->\n");

        tabs += 1;
        fieldOut(tabs, "Name: ");
        ticket.setName(scannerGet("Name", String.class));

        ticket.setCoordinates(parseCoordinates(tabs));

        fieldOut(tabs, "Price: ");
        ticket.setPrice(scannerGet("Price", Integer.class));

        fieldOut(tabs, "Refundable: ");
        ticket.setRfundable(scannerGet("Refundable", Boolean.class));

        fieldOut(tabs, "Ticket Type (VIP, USUAL, BUDGETARY, CHEAP): ");
        try {
            ticket.setType(TicketType.valueOf(scanner.next()));
        } 
        catch(Exception exception) {
            throw new IllegalArgumentException("Ticket Type should be VIP, USUAL, BUDGETARY or CHEAP!", exception);
        }
        ticket.setPerson(parsePerson(tabs));

        return ticket;
    }
    private Long parseID() {
        Long result;
        result = scannerGet("ID", Long.class);
        if(result == null) {
            throw new IllegalArgumentException("ID value can't be null!");
        }
        else if(result <= 0) {
            throw new IllegalArgumentException("ID should be more than 0!");
        }
        return result;
    }
    private String parsePath() {
        String result;
        result = scannerGet("ScriptPath", String.class);
        return result;
    }
    private Boolean parseRefundable() {
        Boolean result;
        result = scannerGet("Refundable", Boolean.class);
        return result;
    }

    public DataContainer parse(String command) throws IllegalArgumentException {

        DataContainer result = new DataContainer();
        result.setCommad(command);
         switch (command) {
            case "add", "add_if_max", "add_if_min", "remove_greater":
                result.add("ticket", skipParseTicket());
                break;
            case "remove_by_id":
                result.add("id", parseID());
                break;
            case "execute_script":
                result.add("path", parsePath());
                break;
            case "count_greater_than_refundable","filter_greater_than_refundable":
                result.add("refundable", parseRefundable());
                break;
            case "update":
                result.add("id", parseID());
                result.add("ticket", parseTicket());
                break;
            case "show", "save", "help", "exit", "average_of_price", "info":
                break;
            default:
                throw new IllegalArgumentException("Command '" + command +  "' not found." );
        }
        return result;
    }
}
