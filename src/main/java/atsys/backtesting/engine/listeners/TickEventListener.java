package atsys.backtesting.engine.listeners;

import atsys.backtesting.components.Strategy;
import atsys.backtesting.components.TickData;
import atsys.backtesting.engine.events.TickEvent;

public class TickEventListener<T extends TickData> implements EventListener<TickEvent<T>> {

    private final Strategy<T> strategy;

    public TickEventListener(Strategy<T> strategy){
        this.strategy = strategy;
    }

    @Override
    public void onEvent(TickEvent<T> event) {
        this.strategy.handleTick(event.getData());
    }
}
