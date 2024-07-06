package atsys.backtesting.engine;

import atsys.backtesting.engine.events.Event;

import java.util.LinkedList;
import java.util.Queue;

public class EventQueueImpl implements EventQueue<Event> {
    private final Queue<Event> queue;
    private final EventConsumer consumer;
    private final EventPublisher publisher;

    public EventQueueImpl(){
        this.queue = new LinkedList<>();
        consumer = new EventConsumer(this);
        publisher = new EventPublisher(this);
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
    public Event peek() {
        return queue.peek();
    }

    @Override
    public void clear() {
        queue.clear();
    }

    @Override
    public EventConsumer getConsumer() {
        return consumer;
    }

    @Override
    public EventPublisher getPublisher() {
        return publisher;
    }

}
