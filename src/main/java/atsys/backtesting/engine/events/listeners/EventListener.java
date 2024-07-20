package atsys.backtesting.engine.events.listeners;


import atsys.backtesting.engine.events.Event;
import atsys.backtesting.engine.exception.BaseException;

public interface EventListener<T extends Event> {
    void onEvent(T event) throws BaseException;
}
