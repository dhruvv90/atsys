package atsys.impl.core;

import atsys.api.core.Event;
import atsys.api.core.EventEmitter;
import atsys.api.core.EventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultEventEmitter implements EventEmitter {
    private final Map<Class<? extends Event>, List<EventListener<? extends Event>>> listenersMap;

    public DefaultEventEmitter(){
        this.listenersMap = new HashMap<>();
    }

    @Override
    public <E extends Event> void register(Class<E> clazz, EventListener<E> listener) {
        listenersMap.computeIfAbsent(clazz, l -> new ArrayList<>()).add(listener);
    }

    @Override
    public <E extends Event> void unregister(Class<E> clazz, EventListener<E> listener) {
        List<EventListener<? extends Event>> listenerValues = listenersMap.get(clazz);
        if(listenerValues != null){
            listenerValues.remove(listener);
            if(listenerValues.isEmpty()){
                listenersMap.remove(clazz);
            }
        }
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
