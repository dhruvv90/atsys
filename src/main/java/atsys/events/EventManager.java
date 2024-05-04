package atsys.events;

import atsys.exceptions.MissingEventListenerException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventManager<E extends Event> {
    private Map<Class<E>, List<EventListener<E>>> listenerMap;

    public EventManager() {
        this.listenerMap = new HashMap<>();
    }


    public void addListener(Class<E> clazz, EventListener<E> listener) {
        if (!listenerMap.containsKey(clazz)) {
            listenerMap.put(clazz, new ArrayList<>());
        }
        listenerMap.get(clazz).add(listener);
    }

    public void invoke(E event) throws EventException {
        Class<? extends Event> clazz = event.getClass();
        if (!listenerMap.containsKey(clazz)) {
            throw new MissingEventListenerException();
        }
        List<EventListener<E>> listenerList = listenerMap.get(clazz);
        for (EventListener<E> listener : listenerList) {
            listener.onEvent(event);
        }
    }
}
