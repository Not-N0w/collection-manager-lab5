package com.labs.client;

import java.time.LocalDate;
import java.util.Scanner;

import com.labs.common.DataContainer;
import com.labs.common.core.Coordinates;
import com.labs.common.core.Location;
import com.labs.common.core.Person;
import com.labs.common.core.Ticket;
import com.labs.common.core.TicketType;

/**
 * Класс-парсер данных комманды.
 */
public class CommandDataParser {
    /** Поле сканнер */
    private Scanner scanner;
    /** Поле флаг: происходит ли чтение из файла */
    private boolean isFileReading = false;

    /**
     * Конструктор - создание нового объекта
     */
    public CommandDataParser(Scanner scanner) {
        this.scanner = scanner;
    }

    /** Метод отключающий вывод приглашений к вводу в консоль */
    public void noComments() {
        isFileReading = true;
    }

    /** Метод включающий вывод приглашений к вводу в консоль */
    public void allowComments() {
        isFileReading = false;
    }

    /**
     * Метод возвращающая данные ввода определенного типа
     * 
     * @param varName имя заполняемого поля (для формирования Exeption.message)
     * @param type    ожидаемы возвращаемый тип
     */
    public <T> T scannerGet(String varName, Class<T> type) {
        try {
            String input = scanner.nextLine().strip();
            if (input.charAt(0) == '!') {
                return scannerGet(varName, type);
            }

            if (input.equals("")) {
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
            throw new IllegalArgumentException(varName + " should be " + type.getSimpleName() + "!", exception);
        }
    }

    /**
     * Метод парсинга координат
     * 
     * @param tabs количество табов (для форматирования приглашений для вввода)
     * @return Возвращает введенные координаты
     * @see Coordinates
     */
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

    /**
     * Метод парсинга дфты
     * 
     * @param dateString дата в строковом представлении ("YYYY-MM-DD")
     * @return Возвращает введенные дату (LocalDate)
     */
    private LocalDate dateParse(String dateString) {
        LocalDate localDate = LocalDate.parse(dateString);
        return localDate;
    }

    /**
     * Метод парсинга локации
     * 
     * @param tabs количество табов (для форматирования приглашений для вввода)
     * @return Возвращает введенную локацию
     * @see Location
     */
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

    /**
     * Метод парсинга персоны
     * 
     * @param tabs количество табов (для форматирования приглашений к ввода)
     * @return Возвращает введенную персону
     * @see Person
     */
    private Person parsePerson(int tabs) {
        Person person = new Person();
        fieldOut(tabs, "Person->\n");
        tabs += 1;
        scanner.nextLine();

        fieldOut(tabs, "Birthday (YYYY-MM-DD): ");
        String date = scannerGet("Birthday", String.class);
        try {
            person.setBirthday(dateParse(date));
        } catch (Exception exception) {
            throw new IllegalArgumentException("Date should be in format YYYY-MM-DD!", exception);
        }

        fieldOut(tabs, "Weight: ");
        person.setWeight(scannerGet("Weight", Double.class));

        fieldOut(tabs, "PassportID: ");
        person.setPassportID(scannerGet("PassportID", String.class));

        person.setLocation(parseLocation(tabs));

        return person;
    }

    /**
     * Метод вывода приглашений к вводу
     * 
     * @param tabs количество табов
     * @param in   выводимое приглашение
     */
    private void fieldOut(int tabs, String in) {
        if (isFileReading)
            return;
        System.out.print("    ".repeat(tabs) + in);
    }

    /**
     * Служебный метод для пропуска строки при парсинге Ticket
     * 
     * @return Возвращает резултат парсинга Ticket
     * @see Ticket
     */
    private Ticket skipParseTicket() {
        scanner.nextLine();
        return parseTicket();
    }

    /**
     * Метод парсинга билета
     * 
     * @param tabs количество табов (для форматирования приглашений к ввода)
     * @return Возвращает введенный билет
     * @see Ticket
     */
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
        } catch (Exception exception) {
            throw new IllegalArgumentException("Ticket Type should be VIP, USUAL, BUDGETARY or CHEAP!", exception);
        }
        ticket.setPerson(parsePerson(tabs));

        return ticket;
    }

    /**
     * Метод парсинга id
     * 
     * @throws IllegalArgumentException выкидывается при id == null
     * @return Возвращает введенный id
     */
    private Long parseID() {
        Long result;
        result = scannerGet("ID", Long.class);
        if (result == null) {
            throw new IllegalArgumentException("ID value can't be null!");
        } else if (result <= 0) {
            throw new IllegalArgumentException("ID should be more than 0!");
        }
        return result;
    }

    /**
     * Метод парсинга пути к файлу
     * 
     * @return Возвращает введенный путь к файлу
     */
    private String parsePath() {
        String result;
        result = scannerGet("ScriptPath", String.class);
        return result;
    }

    /**
     * Метод парсинга поля refundable
     * 
     * @return Возвращает введенный refundable
     */
    private Boolean parseRefundable() {
        Boolean result;
        result = scannerGet("Refundable", Boolean.class);
        return result;
    }

    /**
     * Основной метод парсинга: выбирает метод парсинга в зависимости от введеной
     * команды, формирует результат.
     * 
     * @return контейнер с данными комманды
     * @param command команда, данные которой необходимо считать
     * @see DataContainer
     * @throws IllegalArgumentException если команда не найдена
     */
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
            case "count_greater_than_refundable", "filter_greater_than_refundable":
                result.add("refundable", parseRefundable());
                break;
            case "update":
                result.add("id", parseID());
                result.add("ticket", parseTicket());
                break;
            case "show", "save", "help", "exit", "average_of_price", "info", "clear":
                break;
            default:
                throw new IllegalArgumentException("Command '" + command + "' not found.");
        }
        return result;
    }
}
