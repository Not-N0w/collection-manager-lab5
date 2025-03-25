package com.labs.common.transmitter;

import com.labs.ticketController.TicketController;

/**
 * Класс запроса на серверную часть
 */
public class Request {
    /**
     * Объект, который далее будет храниться на сервере
     */
    TicketController ticketController;

    /**
     * Конструктор - создание нового объекта с определенным ticketController
     * 
     * @param ticketController
     * @see Request#Request()
     */
    public Request(TicketController ticketController) {
        this.ticketController = ticketController;
    }

    /**
     * Конструктор - создание нового объекта
     * 
     * @see Request#Request(TicketController)
     */
    public Request() {
        ticketController = new TicketController();
    }

    /**
     * Создание запроса
     * 
     * @param send байтовое представление объекта, которое нужно отправить на
     *             серверную часть
     */
    public void request(byte[] send) {
        ticketController.process(send);
    }
}
