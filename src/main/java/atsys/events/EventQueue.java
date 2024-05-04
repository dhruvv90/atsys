package atsys.events;

import java.util.LinkedList;

public class EventQueue {
    private LinkedList<Event> queue;

    public EventQueue() {
        this.queue = new LinkedList<>();
    }

    public void put(Event e) {
        this.queue.add(e);
    }

    public Event get() {
        return this.queue.poll();
    }
}
