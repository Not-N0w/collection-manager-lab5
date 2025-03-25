package com.labs.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.labs.client.gson.LocalDateAdapter;
import com.labs.client.gson.LocalDateTimeAdapter;
import com.labs.common.core.Ticket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/** 
 * Класс обрабатывающий файлы 
*/
public class FileManager {
    /** 
     * Поле с классом, отвечающим за получение данных от пользователя. 
    */
    private Input input;

    /** 
     * Поле с классом, отвечающим за вывод данных. 
    */
    private Output output;

    /**
     * Путь к файлу коллекции, который хранит сохраненные объекты коллекции в JSON
     */
    private String collectionFilePath;

    /**
     * Конструктор создание нового объекта.
     * 
     * @param input              класс ввода данных
     * @param output             класс вывода данных
     * @param collectionFilePath путь к файлу коллекции
     */
    public FileManager(Input input, Output output, String collectionFilePath) {
        this.input = input;
        this.output = output;
        this.collectionFilePath = collectionFilePath;
    }

    /**
     * Метод, создающий валидный путь к файлу коллекции (обращаясь к {@link input}
     * для общения с пользователем)
     */
    public void makeValidCollectionFile() {
        String filePath = "";
        try {
            filePath = input.makeCollectionFile();
            collectionFilePath = filePath;
        } catch (Exception exception) {
            output.fileNotExistMessage(exception.getMessage());
            makeValidCollectionFile();
        }
    }

    /**
     * @return путь к файлу коллеции
     */
    public String filePath() {
        return collectionFilePath;
    }

    /**
     * Метод, считывающий JSON из файла коллекции и преобразующий его в списик
     * билетов.
     * 
     * @return список билетов
     * @see Gson
     * @see Ticket
     */
    public ArrayList<Ticket> getTickets() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        String fileData;
        try {
            fileData = getFileData(collectionFilePath);
        } catch (FileNotFoundException exception) {
            return new ArrayList<Ticket>();
        }

        if (fileData.isEmpty())
            return new ArrayList<>();
        Type ticketListType = new TypeToken<ArrayList<Ticket>>() {
        }.getType();
        ArrayList<Ticket> tickets = gson.fromJson(fileData, ticketListType);
        return tickets != null ? tickets : new ArrayList<>();
    }

    /**
     * Метод, преобразующий список билетов в JSON и сохраняющий его в файл коллекции
     * 
     * @param tickets список билетов
     * @see Gson
     * @see Ticket
     */
    public void saveTickets(ArrayList<Ticket> tickets) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        String toFile = gson.toJson(tickets);
        try (FileWriter writer = new FileWriter(collectionFilePath)) {
            writer.write(toFile);
        } catch (IOException e) {
            output.outError("Error writing to file " + e.getMessage());
        }
    }

    /**
     * Метод, считывающий файл
     * 
     * @param filePath путь к файлу
     * @return fileContent данные файла в строковом представлении
     * @throws FileNotFoundException - если файл по {@link filePath} не найден
     */
    public String getFileData(String filePath) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File(filePath));
        fileScanner.useDelimiter("\\A");
        String fileContent = fileScanner.hasNext() ? fileScanner.next() : "";
        fileScanner.close();
        return fileContent;
    }
}
