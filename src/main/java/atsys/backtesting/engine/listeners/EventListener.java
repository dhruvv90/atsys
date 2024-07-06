package atsys.backtesting.engine.listeners;


import atsys.backtesting.engine.events.Event;

public interface EventListener<T extends Event> {
    void onEvent(T event);
}
