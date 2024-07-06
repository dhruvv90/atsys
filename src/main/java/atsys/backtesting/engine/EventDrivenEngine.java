package atsys.backtesting.engine;

import atsys.backtesting.components.strategy.Strategy;
import atsys.backtesting.engine.events.Event;
import atsys.backtesting.engine.events.TickEvent;
import atsys.backtesting.engine.listeners.EventListener;
import atsys.backtesting.engine.listeners.TickEventListener;
import atsys.backtesting.model.Backtest;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a repository of all the components of an event driven system.
 */
public class EventDrivenEngine {

    private final EventQueue<Event> eventQueue;
    private final EventEmitter eventEmitter;

    @Getter
    private final EventConsumer consumer;

    @Getter
    private final EventPublisher publisher;

    private BacktestingContext currentContext;


    EventDrivenEngine(){
        this.eventQueue = new EventQueueImpl();
        this.eventEmitter = new EventEmitter();

        this.consumer = new EventConsumer(this.eventQueue);
        this.publisher = new EventPublisher(this.eventQueue);
    }

    void emitEvent(Event e){
        eventEmitter.emit(e);
    }

    void unregisterBacktest(){
        currentContext.destroy();

        eventEmitter.unregisterAll();
        eventQueue.clear();
    }

    void registerBacktest(Backtest backtest){
        currentContext = new BacktestingContext(backtest, publisher);

        // Register Strategy as TickEventListener
        eventEmitter.register(TickEvent.class, new TickEventListener(backtest.getStrategy()));
    }


    private static class EventEmitter {
        private final Map<Class<? extends Event>, List<EventListener<? extends Event>>> listenersMap;

        EventEmitter(){
            this.listenersMap = new HashMap<>();
        }

        public <E extends Event> void register(Class<E> clazz, EventListener<E> listener) {
            listenersMap.computeIfAbsent(clazz, l -> new ArrayList<>()).add(listener);
        }

        public <E extends Event> void unregister(Class<E> clazz, EventListener<E> listener) {
            List<EventListener<? extends Event>> listenerValues = listenersMap.get(clazz);
            if(listenerValues != null){
                listenerValues.remove(listener);
                if(listenerValues.isEmpty()){
                    listenersMap.remove(clazz);
                }
            }
        }

        public void unregisterAll(){
            listenersMap.clear();
        }


        @SuppressWarnings("unchecked")
        public <E extends Event> void emit(E event) {
            List<EventListener<? extends Event>> listeners = listenersMap.get(event.getClass());
            if(listeners != null){
                for(EventListener<? extends Event> listener: listeners){
                    ((EventListener<E>)listener).onEvent(event);
                }
            }
        }
    }
}
