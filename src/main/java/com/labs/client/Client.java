package com.labs.client;

import java.util.ArrayList;
import com.labs.client.extra.Pair;
import com.labs.common.core.Ticket;

/**
 * Класс-связка клиента. Через него осуществляется вызов клиента извне, а так же запуск основного цикла приложения. 
 */
public class Client {
    /** Поле с классом, отвечающим за получение данных от пользователя. */
    private Input input;
    
    /** Поле с классом, отвечающим за вывод данных. */
    private Output output;

    /** Поле с классом, отвечающим за работу с файлами. */
    private FileManager fileManager;

    /** Поле с классом, отвечающим за обработку данных. */
    private DataManager dataManager;

    /**
     * Полу с экземпляром главного цикла.
     */
    private Cycle cycle;

    /** 
    * Конструктор - создание нового объекта.
    * @see Client#Client(String)
    */
    public Client() {
        this("");
    }
    /** 
    * Конструктор - создание нового объекта с определенным filePath.
    * @see Client#Client()
    */
    public Client(String filePath) {
        output = new Output();
        input = new Input(output);
        fileManager = new FileManager(input, output, filePath);
        dataManager = new DataManager(output);
        cycle = new Cycle(input,output,fileManager,dataManager);
    }
    
    /**
     * Метод, осуществляющий валидацию файла коллекции, отправку этих данных на сервер, зпуск главного цикла.
     */
    @SuppressWarnings("unchecked")
    public void run() {
        fileManager.makeValidCollectionFile();
        ArrayList<Ticket> collectionFileData = fileManager.getTickets();
        dataManager.sendCommand("addSome", new Pair<String,Object>("tickets", collectionFileData));
        dataManager.processResponse();
        cycle.cycle();
    }
}
