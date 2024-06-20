package atsys.api.event;

public interface EventQueue<T extends Event> {

    boolean offer(T event);
    boolean isEmpty();
    T poll();
}
