package com.labs.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.labs.common.DataContainer;


public class Input {
    private Scanner fileScanner;
    private File collectionFile;
    private Scanner scanner;
    private CommandDataParser commandDataParser;

    Input() {
        scanner = new Scanner(System.in);
        commandDataParser = new CommandDataParser(scanner);
    }
    Input(String filePath) {
        this();
        collectionFile = new File(filePath);
    }

    String getCollectionFileData() throws FileNotFoundException {
        fileScanner = new Scanner(collectionFile);
        fileScanner.useDelimiter("\\A");
        String fileContent = fileScanner.hasNext() ? fileScanner.next() : "";
        return fileContent;
    }

    public String getCommand() {
        String command = scanner.next();
        return command;
    }

    public DataContainer getData(String command) throws IllegalArgumentException {
        DataContainer dataContainer = commandDataParser.parse(command);
        return dataContainer;
    }


    public String makeCollectionFile() throws InputMismatchException, FileNotFoundException {
        System.out.print("Enter path of file for collection: ");
        String filePath = scanner.next();
      
        File file = new File(filePath);
        if(!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("File '" + file.getName() + "' created.");
            }
            catch(Exception exception) {
                throw new InputMismatchException("'" +file.getName() + "' is invalid name.");
            }
        }
        collectionFile = file;
        
        return file.getAbsolutePath();
    }
}