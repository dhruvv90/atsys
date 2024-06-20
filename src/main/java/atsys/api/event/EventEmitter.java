package atsys.api.event;

public interface EventEmitter {
    <E extends Event> void register(Class<E> clazz, EventListener<E> listener);
    <E extends Event> void unregister(Class<E> clazz, EventListener<E> listener);
    <E extends Event> void emit(E event);
}
