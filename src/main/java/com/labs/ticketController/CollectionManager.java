package com.labs.ticketController;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
        if(treeSet.isEmpty()) return 0;
        AtomicInteger sumPrice = new AtomicInteger(0);
        treeSet.forEach(x -> sumPrice.addAndGet(x.price()));
        Integer avg = sumPrice.get()/treeSet.size();
        return avg;
    }

    private int booleanToInt(Boolean in) {
        return (in.equals(Boolean.valueOf(true)) ? 1 : 0 );
    }

    public Long countGreaterThanRefundable(Boolean refundable) {
        Long count = treeSet.stream()
        .filter(item -> booleanToInt(item.refundable()) > booleanToInt(refundable)) // absurdly, but according to TOR
        .count();

        return count;
    }

    public ArrayList<Ticket> filterGreaterThanRefundable(Boolean refundable) {
        ArrayList<Ticket> trickets = (ArrayList<Ticket>) treeSet.stream()
            .filter(item -> booleanToInt(item.refundable()) > booleanToInt(refundable))  // absurdly, but according to TOR
            .collect(Collectors.toCollection(ArrayList::new));

        return trickets;
    }

    public void addIfMax(Ticket ticket) {
        if(ticket.compareTo(treeSet.last()) > 0) {
            treeSet.add(ticket);
        }
    }
    public void addIfMin(Ticket ticket) {
        if(ticket.compareTo(treeSet.last()) < 0) {
            treeSet.add(ticket);
        }
    }

    public void removeGreater(Ticket ticket) {
        treeSet.removeIf(item -> (item.compareTo(ticket) < 0));
    }
    public String getInfo() {
        String result = treeSet.getClass().toString() + '\n';
        result += "Number of elements: " + treeSet.size() + '\n';
        return result;
    }
}

