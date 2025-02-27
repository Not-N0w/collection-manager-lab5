package com.labs.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.labs.client.gson.LocalDateAdapter;
import com.labs.client.gson.LocalDateTimeAdapter;
import com.labs.common.core.Ticket;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {
    private Input input;
    private Output output;
    private String collectionFilePath;

    public FileManager(Input input, Output output, String collectionFilePath) {
        this.input = input;
        this.output = output;
        this.collectionFilePath = collectionFilePath;

    }

    public void makeValidCollectionFile() {
        String filePath = "";
        try {
            filePath = input.makeCollectionFile();
            collectionFilePath = filePath;
        } 
        catch(Exception exception) {
            output.fileNotExistMessage(exception.getMessage());
            makeValidCollectionFile();
        }
    }

    public String filePath() {
        return collectionFilePath;
    }

    public ArrayList<Ticket> getTickets() throws FileNotFoundException {
        Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();

        String fileData = input.getFileData(collectionFilePath);
        if(fileData.isEmpty()) return new ArrayList<>();
        Type ticketListType = new TypeToken<ArrayList<Ticket>>() {}.getType();
        ArrayList<Ticket> tickets = gson.fromJson(fileData, ticketListType);
        return tickets != null ? tickets : new ArrayList<>();
    }

    public void saveTickets(ArrayList<Ticket> tickets) {
        Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();

        String toFile = gson.toJson(tickets);
        try (FileWriter writer = new FileWriter(collectionFilePath)) {
            writer.write(toFile);
        }
        catch (IOException e) {
            output.outError("Error writing to file " + e.getMessage());
        }
    }

    public String getRecoursesFileData(String filename) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        if (inputStream == null) {
            return "";
        }
        Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
        String data = scanner.useDelimiter("\\A").next();
        scanner.close();
        return data;
    }
}
