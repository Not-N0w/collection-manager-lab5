package com.labs.client.localCommandManager.commands;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import com.labs.common.Command;

public class HelpCommand implements Command  {

    public Object execute()  {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("help.txt");
        if (inputStream == null) {
            return "";
        }
        Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
        String data = scanner.useDelimiter("\\A").next();
        scanner.close();
        return data;
    }
}
