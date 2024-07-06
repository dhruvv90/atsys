package atsys.backtesting.engine;

import atsys.backtesting.engine.event.Event;

public class EventConsumer {

    private final EventQueue<Event> eventQueue;

    public EventConsumer(EventQueue<Event> eventQueue){
        this.eventQueue = eventQueue;
    }

    public boolean hasEvents(){
        return !eventQueue.isEmpty();
    }

    public Event peekEvent(){
        return eventQueue.peek();
    }

    public Event getEvent(){
        return eventQueue.poll();
    }
}
