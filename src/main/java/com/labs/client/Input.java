package com.labs.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.labs.common.DataContainer;


public class Input {

    private Scanner scanner;
    private CommandDataParser commandDataParser;

    Input() {
        scanner = new Scanner(System.in);
        commandDataParser = new CommandDataParser(scanner);
    }
    Input(String filePath) throws IOException {
        scanner = new Scanner(new File(filePath), StandardCharsets.UTF_8);
        commandDataParser = new CommandDataParser(scanner);
    }
    public String getCommand() {
        String command = scanner.next();
        return command;
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
        
        return file.getAbsolutePath();
    }

    public DataContainer getData(String command) throws IllegalArgumentException {
        DataContainer dataContainer = commandDataParser.parse(command);
        return dataContainer;
    }

    String getFileData(String filePath) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File(filePath));
        fileScanner.useDelimiter("\\A");
        String fileContent = fileScanner.hasNext() ? fileScanner.next() : "";
        fileScanner.close();
        return fileContent;
    }

}