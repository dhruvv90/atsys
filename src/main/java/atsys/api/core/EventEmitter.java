package atsys.api.core;

import atsys.api.core.event.Event;

public interface EventEmitter {
    <E extends Event> void register(Class<E> clazz, EventListener<E> listener);
    <E extends Event> void unregister(Class<E> clazz, EventListener<E> listener);
    <E extends Event> void emit(E event);
}
