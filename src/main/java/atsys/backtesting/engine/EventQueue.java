package atsys.backtesting.engine;

import atsys.backtesting.engine.events.Event;

public interface EventQueue<T extends Event> {

    boolean offer(T event);
    boolean isEmpty();
    T poll();
    T peek();
    void clear();

    EventConsumer getConsumer();
    EventPublisher getPublisher();
}
