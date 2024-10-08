package atsys.backtesting.engine;


import atsys.backtesting.engine.events.Event;
import atsys.backtesting.engine.events.listeners.EventListener;
import atsys.backtesting.engine.exception.BaseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    private final Map<Class<? extends Event>, List<EventListener<? extends Event>>> listenersMap;

    public EventManager() {
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
    public <E extends Event> void emit(E event) throws BaseException {
        List<EventListener<? extends Event>> listeners = listenersMap.get(event.getClass());
        if (listeners != null) {
            for (EventListener<? extends Event> listener : listeners) {
                ((EventListener<E>) listener).onEvent(event);
            }
        }
    }
}