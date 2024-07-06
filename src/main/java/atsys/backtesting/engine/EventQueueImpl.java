package atsys.backtesting.engine;

import atsys.backtesting.engine.event.Event;

import java.util.LinkedList;
import java.util.Queue;

public class EventQueueImpl implements EventQueue<Event> {
    private final Queue<Event> queue;

    public EventQueueImpl(){
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
