package atsys.backtesting.engine.events.listeners;

import atsys.backtesting.engine.components.Strategy;
import atsys.backtesting.engine.components.TickData;
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
