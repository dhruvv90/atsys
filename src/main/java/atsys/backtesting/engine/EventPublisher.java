package atsys.backtesting.engine;

import atsys.backtesting.engine.events.Event;

import java.time.Instant;

public class EventPublisher {
    private final EventQueue<Event> eventQueue;

    EventPublisher(EventQueue<Event> eventQueue){
        this.eventQueue = eventQueue;
    }

    public void publishEvent(Event event){
        event.setPublishedAt(Instant.now());
        this.eventQueue.offer(event);
    }

}
