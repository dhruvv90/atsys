package atsys.impl.event;

import atsys.api.event.Event;
import atsys.api.event.EventQueue;

import java.util.LinkedList;
import java.util.Queue;

public class DefaultEventQueue implements EventQueue<Event> {
    private final Queue<Event> queue;

    public DefaultEventQueue(){
        this.queue = new LinkedList<>();
    }

    @Override
    public boolean offer(Event event) {
        return queue.offer(event);
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }


    @Override
    public Event poll() {
        return queue.poll();
    }
}
