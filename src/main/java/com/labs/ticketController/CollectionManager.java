package com.labs.ticketController;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

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
    public void removeById(Long id) {
        treeSet.removeIf(ticket -> (ticket.id() == id));
    }
    public void clear() {
        treeSet.clear();
    }

    public Integer averageOfPrice() {
        AtomicInteger sumPrice = new AtomicInteger(0);
        treeSet.forEach(x -> sumPrice.addAndGet(x.price()));
        Integer avg = sumPrice.get()/treeSet.size();
        return avg;
    }


}
