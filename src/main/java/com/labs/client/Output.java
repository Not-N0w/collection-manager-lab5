package com.labs.client;

import java.util.ArrayList;

import com.labs.common.DataContainer;
import com.labs.common.core.Ticket;

public class Output {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private boolean isCommentsAllowed = true;

    public void noComments() {
        isCommentsAllowed = false;
    }
    public void allowComments() {
        isCommentsAllowed = true;
    }

    public void fileNotExistMessage(String filePath) {
        out("File '" + filePath + "' not found.");
    }
    
    public void outWaiting() {
        if(!isCommentsAllowed) return;
        System.out.println(ANSI_BLUE +  "Waiting for commands" + ANSI_RESET);
    }

    public void out(String inString) {
        System.out.println(inString);
    }

    public void outError(String inString) {
        System.out.println(ANSI_RED +  inString + ANSI_RESET);
    }

    public void outSub(String inString) {
        if(!isCommentsAllowed) return;
        System.out.println(ANSI_BLUE +  inString + ANSI_RESET);
    }
    public void outOk(String inString) {
        if(!isCommentsAllowed) return;
        System.out.println(ANSI_GREEN +  inString + ANSI_RESET);
    }


    public void responseOut(DataContainer response) {
        if(response == null) return;
        if(((String)response.get("status")).equals("error")) {
            outError((String)response.get("message"));
            return;
        }
        if(response.getCommand().equals("addSome")) {
            outOk("Data succsessfully loaded!");
            return;
        }
        outOk((String)response.get("message"));
        

        Object responseData = response.get("data");
        if(responseData == null) return;
        outSub("OUTPUT:");

        String command = response.getCommand();
        out("-- " + ANSI_PURPLE + command + ANSI_RESET +" " + "-".repeat(50 - command.length() - 4));

        if(responseData instanceof ArrayList<?>) {
            @SuppressWarnings("unchecked")
            ArrayList<Ticket> tickets = (ArrayList<Ticket>)responseData;
            for(var ticket : tickets) {
                out(ticket.toString() + "\n");
            }
        }
        else if(responseData instanceof Integer) {
            Integer number = (Integer)responseData;
            out(String.valueOf(number));
        }
        else if(responseData instanceof Long) {
            Long number = (Long)responseData;
            out(String.valueOf(number));
        }
        else if(responseData instanceof String) {
            String text = (String)responseData;
            out(text);
        }
        out("-".repeat(50));
    }
}
