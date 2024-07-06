package atsys.backtesting.engine.listeners;

import atsys.backtesting.components.ExecutionManager;
import atsys.backtesting.engine.events.OrderEvent;

public class OrderEventListener implements EventListener<OrderEvent> {

    private final ExecutionManager executionManager;

    public OrderEventListener(ExecutionManager executionManager){
        this.executionManager = executionManager;
    }

    @Override
    public void onEvent(OrderEvent event) {
        this.executionManager.processOrder(event);
    }
}
