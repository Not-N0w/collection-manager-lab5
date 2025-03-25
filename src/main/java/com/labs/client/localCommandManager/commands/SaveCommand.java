package com.labs.client.localCommandManager.commands;

import java.util.ArrayList;

import com.labs.client.DataManager;
import com.labs.client.FileManager;
import com.labs.common.Command;
import com.labs.common.DataContainer;
import com.labs.common.core.Ticket;

/**
 * Класс команды save.
 */
public class SaveCommand implements Command {
    /**
     * Поле с классом, отвечающим за работу с файлами.
    */
    private FileManager fileManager;

    /**
     * Поле с классом, отвечающим за обработку данных.
    */
    private DataManager dataManager;

    /**
     * Конструктор - создание нового объекта.
     * 
     * @param dataManager класс обработки данных
     * @param fileManager класс работы с файлами
     */
    public SaveCommand(FileManager fileManager, DataManager dataManager) {
        this.fileManager = fileManager;
        this.dataManager = dataManager;
    }

    /**
     * Метод, исполняющий команду save.
     * 
     * @return Строку "Saved succsessfully!"
     */
    @SuppressWarnings("unchecked")
    public Object execute() {
        dataManager.sendCommand("show");
        DataContainer response = dataManager.getResponse();
        fileManager.saveTickets((ArrayList<Ticket>) response.get("data"));
        return "Saved succsessfully!";
    }
}