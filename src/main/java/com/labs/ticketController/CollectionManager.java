package com.labs.ticketController;

import java.util.ArrayList;
import java.util.TreeSet;

import com.labs.common.core.Ticket;

public class CollectionManager {
    private TreeSet<Ticket> treeSet;
    
    public CollectionManager() {
        treeSet = new TreeSet<>();
    }
    public void add(Ticket ticket) {
        treeSet.add(ticket);
    }
    public void update(Long id, Ticket ticket) {
        Ticket toRemove = treeSet.stream()
                             .filter(t -> t.id().equals(id))
                             .findFirst()
                             .orElse(null);

        if (toRemove != null) {
            treeSet.remove(toRemove);
        }
        ticket.setId(id);
        treeSet.add(ticket);
    }
    public ArrayList<Ticket> getAll() {
        ArrayList<Ticket> tickets = new ArrayList<>(treeSet);
        return tickets;
    }
    


}
