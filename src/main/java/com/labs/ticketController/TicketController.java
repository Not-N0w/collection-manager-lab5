package com.labs.ticketController;

import java.io.IOException;
import com.labs.common.DataContainer;
import com.labs.common.dataConverter.Deserializer;
import com.labs.common.dataConverter.Serializer;
import com.labs.common.transmitter.Response;

/**
 * Класс - распределитель на серверной части
 */
public class TicketController {
    /**
     * Ответ от серверной части
     */
    private Response response;
    /**
     * Вызыватель команды
     */
    private Invoker invoker;

    /**
     * Конструктор - создание нового объекта.
     */
    public TicketController() {
        response = Response.getInstance();
        invoker = new Invoker();
    }

    /**
     * Обработка данных
     * 
     * @param inData данные с клиента в байтовом представлении
     */
    public void process(byte[] inData) {
        DataContainer data = null;
        DataContainer commandResponse = new DataContainer();
        try {
            data = Deserializer.deserialize(inData);
        } catch (IOException exception) {
            commandResponse.add("status", "error");
            commandResponse.add("message", "Deserialization error (IO).");
        } catch (ClassNotFoundException exception) {
            commandResponse.add("status", "error");
            commandResponse.add("message", "Deserialization error. Invalid class.");
        }

        invoker.run(data);
        commandResponse = invoker.getResponse();

        byte[] outData;
        try {
            outData = Serializer.serialize(commandResponse);
            response.setResponse(outData);
        } catch (IOException exception) {
            // idk mb try again or sth else
        }
    }
}
