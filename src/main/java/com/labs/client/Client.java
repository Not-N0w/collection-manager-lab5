package com.labs.client;

import java.util.ArrayList;
import com.labs.client.extra.Pair;
import com.labs.common.core.Ticket;

public class Client {
    private Input input;
    private Output output;
    private FileManager fileManager;
    private DataManager dataManager;

    private Cycle cycle;

    public Client(String filePath) {
        output = new Output();
        input = new Input(output);
        fileManager = new FileManager(input, output, filePath);
        dataManager = new DataManager(output);
        cycle = new Cycle(input,output,fileManager,dataManager);
    }
    public Client() {
        this("");
    }
    

    @SuppressWarnings("unchecked")
    public void run() {
        fileManager.makeValidCollectionFile();
        ArrayList<Ticket> collectionFileData = fileManager.getTickets();
        dataManager.sendCommand("addSome", new Pair<String,Object>("tickets", collectionFileData));
        dataManager.processResponse();
        cycle.cycle();
    }
}
