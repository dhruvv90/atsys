package atsys.backtesting.engine.listeners;

import atsys.backtesting.components.Strategy;
import atsys.backtesting.engine.events.TickEvent;

public class TickEventListener implements EventListener<TickEvent> {

    private final Strategy strategy;

    public TickEventListener(Strategy strategy){
        this.strategy = strategy;
    }

    @Override
    public void onEvent(TickEvent event) {
        this.strategy.handleTick(event.getData());
    }
}
