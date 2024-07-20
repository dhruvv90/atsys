package atsys.backtesting.engine;

import atsys.backtesting.engine.components.TickData;
import atsys.backtesting.engine.events.Event;
import atsys.backtesting.engine.events.TickEvent;
import lombok.Getter;

import java.time.Instant;

public class QueuePublisher {
    private final EventQueue<Event> eventQueue;

    @Getter
    private TickData lastTick;

    QueuePublisher(EventQueue<Event> eventQueue){
        this.eventQueue = eventQueue;
    }

    public void publishEvent(Event event){
        event.setPublishedAt(Instant.now());
        this.eventQueue.offer(event);

        if(event.getClass().isAssignableFrom(TickEvent.class)){
            this.lastTick = ((TickEvent<?>) event).getData();
        }
    }

}
