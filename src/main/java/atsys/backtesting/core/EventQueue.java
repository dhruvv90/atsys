package atsys.backtesting.core;

import atsys.backtesting.core.event.Event;

public interface EventQueue<T extends Event> {

    boolean offer(T event);
    boolean isEmpty();
    T poll();
    void clear();
}
