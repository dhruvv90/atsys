package atsys.backtesting.engine;

import atsys.backtesting.engine.events.Event;

import java.time.Instant;

public class EventConsumer {

    private final EventQueue<Event> eventQueue;

    EventConsumer(EventQueue<Event> eventQueue){
        this.eventQueue = eventQueue;
    }

    public boolean hasEvents(){
        return !eventQueue.isEmpty();
    }

    public Event peekEvent(){
        return eventQueue.peek();
    }

    public Event consumeEvent(){
        Event first = peekEvent();
        if(first != null){
            first.setConsumedAt(Instant.now());
        }
        return eventQueue.poll();
    }
}
