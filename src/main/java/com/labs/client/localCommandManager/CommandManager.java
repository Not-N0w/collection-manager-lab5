package com.labs.client.localCommandManager;

import com.labs.client.Cycle;
import com.labs.client.DataManager;
import com.labs.client.FileManager;
import com.labs.common.DataContainer;

/** Класс - менеджер команд */
public class CommandManager {
    /** Поле - исполнитель команды */
    private Invoker invoker;

    /**
     * Конструктор - создание нового объекта.
     * 
     * @param cycle       цикл, в котором сейчас находится программа
     * @param fileManager класс работы с файлами
     * @param dataManager класс обработки данных
     */
    public CommandManager(Cycle cycle, FileManager fileManager, DataManager dataManager) {
        invoker = new Invoker(cycle, fileManager, dataManager);
    }

    /**
     * Метод, запускающий исполнение команды
     * 
     * @param dataContainer данные команды
     * @return ответ исполнителя
     */
    public DataContainer executeCommand(DataContainer dataContainer) {
        if (!invoker.check(dataContainer.getCommand()))
            return null;
        invoker.run(dataContainer);

        return invoker.getResponse();
    }
}
