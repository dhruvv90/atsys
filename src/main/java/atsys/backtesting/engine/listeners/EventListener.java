package atsys.backtesting.engine.listeners;


import atsys.backtesting.engine.event.Event;

public interface EventListener<T extends Event> {
    void onEvent(T event);
}
