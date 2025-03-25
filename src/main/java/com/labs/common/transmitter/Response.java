package com.labs.common.transmitter;

/**
 * Класс - ответ с сервера
 */
public class Response {
    /**
     * Singleton объект
     */
    private static Response instance;
    /**
     * Ответ с сервера в байтовом представлении
     */
    public byte[] response;

    /**
     * Запрет на создание объектов снаружи
     */
    private Response() {
    }

    /**
     * @return единственный в программе объект Response
     */
    public static Response getInstance() {
        if (instance == null) {
            instance = new Response();
        }
        return instance;
    }

    /**
     * Устанавливает ответ сервера
     * 
     * @param dc ответ в байтовом представлении
     */
    public void setResponse(byte[] dc) {
        response = dc;
    }

    /**
     * @return возвращает ответ сервера
     */
    public byte[] getResponse() {
        return response;
    }
}
