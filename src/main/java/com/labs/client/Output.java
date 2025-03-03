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
    private int lengthWithoutColor = 0;

    public void noComments() {
        isCommentsAllowed = false;
    }
    public void allowComments() {
        isCommentsAllowed = true;
    }
    
    private String makeBlock(String inner, String header) {
        String result = "-- " + header + " " + "-".repeat(46 - lengthWithoutColor) + '\n';
        result += inner + '\n';
        result += "-".repeat(50) + '\n';
        return result;
    }

    private String makeError(String in) {
        lengthWithoutColor = 6;
        return makeBlock(ANSI_RED + in + ANSI_RESET, ANSI_RED + "ERROR" + ANSI_RESET);
    }
    private String makeOk(String in) {
        if(!isCommentsAllowed) return "";
        lengthWithoutColor = 2;
        return makeBlock(ANSI_GREEN + in + ANSI_RESET, ANSI_GREEN + "OK" + ANSI_RESET);
    }
    public String fileNotExistMessage(String filePath) {
        return makeError("File '" + filePath + "' not found.");
    }
    public void out(String out) {
        System.out.print(out);
    }
    public void waiting() {
        if(!isCommentsAllowed) return;
        out(ANSI_BLUE + "Waiting for commands\n" + ANSI_RESET);
    }
    public void outError(String in) {
        out(makeError(in));
    }


    public void responseOut(DataContainer response) {
        if(response == null) return;
        if(((String)response.get("status")).equals("error")) {
            out(makeError((String)response.get("message")));
            out("\n");
            return;
        }
        if(response.getCommand().equals("addSome")) {
            out(makeOk("Data succsessfully loaded!"));
            out("\n");
            return;
        }
        out(makeOk((String)response.get("message")));
        

        Object responseData = response.get("data");
        if(responseData == null) {
            out("\n");
            return;
        }


        String content = "";
        if(responseData instanceof ArrayList<?>) {
            @SuppressWarnings("unchecked")
            ArrayList<Ticket> tickets = (ArrayList<Ticket>)responseData;
            for(var ticket : tickets) {
                content += "> " + ticket.toString();
            }
        }
        else if(responseData instanceof Integer) {
            Integer number = (Integer)responseData;
            content = String.valueOf(number);
        }
        else if(responseData instanceof Long) {
            Long number = (Long)responseData;
            content = String.valueOf(number);
        }
        else if(responseData instanceof String) {
            String text = (String)responseData;
            content = text;
        }
        
        String command = response.getCommand();
        lengthWithoutColor = command.length() + 10;
        out(makeBlock(content, ANSI_PURPLE + response.getCommand() + " -> " + "OUTPUT" + ANSI_RESET));
        out("\n");

    }
}
