package com.labs.client;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.labs.common.DataContainer;


public class Input {
    private Scanner scanner;
    private CommandDataParser commandDataParser;
    private Output output;

    public Input(Output output) {
        this.output = output;
        scanner = new Scanner(System.in);
        commandDataParser = new CommandDataParser(scanner);
    }
    public Input(Output output, String filePath) {
        this.output = output;
        this.output = output;
        scannerInit(filePath);
        commandDataParser = new CommandDataParser(scanner);
    }

    private void scannerInit(String filePath) {
        try {
            scanner = new Scanner(new File(filePath.strip()));
        }
        catch(Exception exception) {
            output.outError("Something wrong with input file(");
        }
    }

    public String getCommand() {
        if(!scanner.hasNext()) throw new NoSuchElementException("Input is clear.");
        String command = scanner.next();
        return command;
    }
    public String makeCollectionFile() throws InputMismatchException {
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
    public void noComments() {
        commandDataParser.noComments();
    }
    public void allowComments() {
        commandDataParser.allowComments();
    }
    public DataContainer getData(String command) {
        try {
            DataContainer dataContainer = commandDataParser.parse(command);
            return dataContainer;
        }
        catch(IllegalArgumentException exception) {
            output.outError("Input error: " + exception.getMessage());
        }
        return null;
    }



}