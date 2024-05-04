package atsys.events;


public interface EventListener<E extends Event> {
    void onEvent(E event);
}
