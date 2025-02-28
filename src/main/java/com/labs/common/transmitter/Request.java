package com.labs.common.transmitter;

import com.labs.ticketController.TicketController;

public class Request {
    TicketController ticketController;
    
    public Request(TicketController ticketController) {
        this.ticketController = ticketController;
    }
    public Request() {
        ticketController = new TicketController();
    }

    public void request(byte[] send) {
        ticketController.process(send);
    }
}
