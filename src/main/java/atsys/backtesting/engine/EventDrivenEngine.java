package atsys.backtesting.engine;

import atsys.backtesting.engine.events.Event;
import atsys.backtesting.engine.events.KillEvent;
import atsys.backtesting.engine.events.TickEvent;
import atsys.backtesting.engine.listeners.EventListener;
import atsys.backtesting.engine.listeners.KillEventListener;
import atsys.backtesting.engine.listeners.TickEventListener;
import atsys.backtesting.model.Backtest;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Re-usable Event Driven Engine used by the backtester
 */
public class EventDrivenEngine {

    private final EventQueue<Event> eventQueue;
    private final EventsRepository eventsRepository;

    @Getter
    private final EventConsumer consumer;

    @Getter
    private final EventPublisher publisher;

    private BacktestingContext currentContext;


    EventDrivenEngine() {
        this.eventQueue = new EventQueueImpl();
        this.eventsRepository = new EventsRepository();

        this.consumer = new EventConsumer(this.eventQueue);
        this.publisher = new EventPublisher(this.eventQueue);
    }

    void emitEvent(Event e) {
        eventsRepository.emit(e);
    }

    void reset() {
        currentContext.destroy();

        eventsRepository.unregisterAll();
        eventQueue.clear();
    }

    void initializeForBacktest(Backtest backtest) {
        currentContext = new BacktestingContext(backtest, publisher);

        // Register Strategy as TickEventListener
        eventsRepository.register(TickEvent.class, new TickEventListener(backtest.getStrategy()));

        // Register KillEvent
        eventsRepository.register(KillEvent.class, new KillEventListener());
    }


    private static class EventsRepository {
        private final Map<Class<? extends Event>, List<EventListener<? extends Event>>> listenersMap;

        EventsRepository() {
            this.listenersMap = new HashMap<>();
        }

        public <E extends Event> void register(Class<E> clazz, EventListener<E> listener) {
            listenersMap.computeIfAbsent(clazz, l -> new ArrayList<>()).add(listener);
        }

        public <E extends Event> void unregister(Class<E> clazz, EventListener<E> listener) {
            List<EventListener<? extends Event>> listenerValues = listenersMap.get(clazz);
            if (listenerValues != null) {
                listenerValues.remove(listener);
                if (listenerValues.isEmpty()) {
                    listenersMap.remove(clazz);
                }
            }
        }

        public void unregisterAll() {
            listenersMap.clear();
        }


        @SuppressWarnings("unchecked")
        public <E extends Event> void emit(E event) {
            List<EventListener<? extends Event>> listeners = listenersMap.get(event.getClass());
            if (listeners != null) {
                for (EventListener<? extends Event> listener : listeners) {
                    ((EventListener<E>) listener).onEvent(event);
                }
            }
        }
    }
}
