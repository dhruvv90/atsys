package atsys.backtesting.engine;

import atsys.backtesting.engine.event.Event;

public class EventPublisher {
    private final EventQueue<Event> eventQueue;

    public EventPublisher(EventQueue<Event> eventQueue){
        this.eventQueue = eventQueue;
    }

    public void publishEvent(Event event){
        this.eventQueue.offer(event);
    }

}
