package atsys.api.core;


import atsys.api.core.event.Event;

public interface EventListener<T extends Event> {
    void onEvent(T event);
}
