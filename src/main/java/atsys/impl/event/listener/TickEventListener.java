package atsys.impl.event.listener;

import atsys.api.components.Strategy;
import atsys.api.core.EventListener;
import atsys.impl.event.TickEvent;

public class TickEventListener implements EventListener<TickEvent> {

    private final Strategy strategy;

    public TickEventListener(Strategy strategy){
        this.strategy = strategy;
    }

    @Override
    public void onEvent(TickEvent event) {
        this.strategy.handleData(event.getData());
    }
}
