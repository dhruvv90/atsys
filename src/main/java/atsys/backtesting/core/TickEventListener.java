package atsys.backtesting.core;

import atsys.backtesting.components.Strategy;
import atsys.backtesting.core.event.TickEvent;

public class TickEventListener implements EventListener<TickEvent> {

    private final Strategy strategy;

    public TickEventListener(Strategy strategy){
        this.strategy = strategy;
    }

    @Override
    public void onEvent(TickEvent event) {
        this.strategy.handleTick(event);
    }
}
