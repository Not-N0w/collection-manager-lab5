package com.labs.client;

import java.io.IOException;

import com.labs.client.extra.Pair;
import com.labs.common.DataContainer;
import com.labs.common.dataConverter.Deserializer;
import com.labs.common.dataConverter.Serializer;
import com.labs.common.transmitter.Request;
import com.labs.common.transmitter.Response;

/**
 * Класс - обработчик данных. Организует отправку запросов и получение ответов с
 * сервера, а так же первичную обработку ответов.
 */
public class DataManager {
    /** 
     * Поле - запрос 
    */
    private Request request;

    /** 
     * Поле - ответ 
    */
    private Response response;

    /** 
     * Поле с классом, отвечающим за вывод данных.
    */
    private Output output;

    /**
     * Конструктор - создание нового объекта.
     * 
     * @param output класс вывода данных
     */
    public DataManager(Output output) {
        request = new Request();
        response = Response.getInstance();
        this.output = output;
    }

    /**
     * Метод, сериализующий данные и отправляющий их на сервер
     * 
     * @param commandData
     * @return true, если зпрос успешно сериализван и отправлен, false - в противном
     *         случае.
     * @see DataContainer
     */
    public boolean send(DataContainer commandData) {
        byte[] serialized;
        try {
            serialized = Serializer.serialize(commandData);
            request.request(serialized);
        } catch (IOException exception) {
            output.outError("Serialization error");
            output.outError("Try again\n");
            return false;
        }
        return true;
    }

    /**
     * @param command комманда (без данны)
     * @param pairs   данные команды представленные в виде пар
     * @return результат метода {@link DataManager#send(DataContainer)}
     * @see Pair
     * @see DataContainer
     */
    public boolean sendCommand(String command, @SuppressWarnings("unchecked") Pair<String, Object>... pairs) {
        DataContainer dataContainer = new DataContainer(command);
        for (var item : pairs) {
            dataContainer.add(item.key(), item.value());
        }
        return send(dataContainer);
    }

    /**
     * Метод получающий ответ с сервера, десериализующий его и возвращающий данные
     * ответа
     * 
     * @return {@link DataContainer} данными ответа
     * @see DataContainer
     */
    public DataContainer getResponse() {
        DataContainer commandResponse;
        try {
            commandResponse = Deserializer.deserialize(response.getResponse());
            return commandResponse;
        } catch (IOException exception) {
            output.outError("Response derialization error (IO).");
            output.outError("Try again\n");
            return null;
        } catch (ClassNotFoundException exception) {
            output.outError("Response derialization error. Invalid class.");
            output.outError("Try again\n");
            return null;
        }
    }

    /**
     * Метод получающий ответ с сервера и проверяющий его валидность
     * 
     * @see DataContainer
     */
    public void processResponse() {
        DataContainer commandResponse;
        try {
            commandResponse = Deserializer.deserialize(response.getResponse());
            output.responseOut(commandResponse);
        } catch (IOException exception) {
            output.outError("Response derialization error (IO).");
            output.outError("Try again\n");
        } catch (ClassNotFoundException exception) {
            output.outError("Response derialization error. Invalid class.");
            output.outError("Try again\n");
        }
    }
}
