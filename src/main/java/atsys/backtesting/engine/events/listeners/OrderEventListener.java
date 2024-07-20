package atsys.backtesting.engine.events.listeners;

import atsys.backtesting.engine.components.ExecutionManager;
import atsys.backtesting.engine.events.OrderEvent;
import atsys.backtesting.engine.exception.BaseException;

public class OrderEventListener implements EventListener<OrderEvent> {

    private final ExecutionManager executionManager;

    public OrderEventListener(ExecutionManager executionManager){
        this.executionManager = executionManager;
    }

    @Override
    public void onEvent(OrderEvent event) throws BaseException {
        this.executionManager.processOrder(event.getOrder());
    }
}
