package atsys.backtesting.engine;

import atsys.backtesting.Backtest;
import atsys.backtesting.components.strategy.Strategy;
import atsys.backtesting.engine.event.Event;
import atsys.backtesting.engine.event.TickEvent;
import atsys.backtesting.engine.listeners.TickEventListener;
import lombok.Getter;

public class EventDrivenEngine {
    private final EventQueue<Event> eventQueue;

    @Getter
    private final EventConsumer eventConsumer;

    @Getter
    private final EventPublisher eventPublisher;

    private final EventEmitter eventEmitter;


    public EventDrivenEngine(){
        this.eventQueue = new EventQueueImpl();
        this.eventEmitter = new EventEmitter();

        this.eventConsumer = new EventConsumer(eventQueue);
        this.eventPublisher = new EventPublisher(eventQueue);
    }

    public void run(Backtest backtest){



        this.reset();
    }

    private void reset(){
        this.eventQueue.clear();
    }

    public void registerStrategy(Strategy strategy){
        TickEventListener tickListener = new TickEventListener(strategy);
        eventEmitter.register(TickEvent.class, tickListener);
    }

    public void processEvent(Event e){
        eventEmitter.emit(e);
    }

}
