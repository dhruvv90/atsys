package atsys.api.core;

public interface EventQueue<T extends Event> {

    boolean offer(T event);
    boolean isEmpty();
    T poll();
}
