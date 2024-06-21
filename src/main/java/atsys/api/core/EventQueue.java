package atsys.api.core;

import atsys.api.core.event.Event;

public interface EventQueue<T extends Event> {

    boolean offer(T event);
    boolean isEmpty();
    T poll();
}
