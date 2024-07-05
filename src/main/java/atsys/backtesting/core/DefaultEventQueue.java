package atsys.backtesting.core;

import atsys.backtesting.core.event.Event;

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

    @Override
    public void clear() {
        queue.clear();
    }

}
