package atsys.backtesting.core;


import atsys.backtesting.core.event.Event;

public interface EventListener<T extends Event> {
    void onEvent(T event);
}
