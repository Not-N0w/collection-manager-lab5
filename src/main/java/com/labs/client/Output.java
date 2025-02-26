package com.labs.client;

import com.labs.common.DataContainer;

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


    public void fileNotExistMessage(String filePath) {
        out("File '" + filePath + "' not found.");
    }
    public void successfulFileDataLoaded(String filePath) {
        out("Data from the file '" + filePath + "' successfully loaded.");
    }

    public void out(String inString) {
        System.out.println(inString);
    }

    public void outError(String inString) {
        System.out.println(ANSI_RED +  inString + ANSI_RESET);
    }

    public void outSub(String inString) {
        System.out.println(ANSI_BLUE +  inString + ANSI_RESET);
    }

    public void responseOut(DataContainer response) {
        if(response == null) return;
        if((String)response.get("status") == "Error") {
            outError((String)response.get("message"));
        }
        else {
            outSub((String)response.get("message"));
        }
        //data out
    }
}
