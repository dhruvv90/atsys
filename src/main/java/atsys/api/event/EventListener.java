package atsys.api.event;


public interface EventListener<T extends Event> {
    void onEvent(T event);
}
