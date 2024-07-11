package atsys.backtesting.components.impl;

import atsys.backtesting.components.ExecutionManager;
import atsys.backtesting.engine.events.FillEvent;
import atsys.backtesting.engine.events.OrderEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Execution")
public class SimulatedExecutionManager extends ExecutionManager {

    @Override
    public void processOrder(OrderEvent event) {
        log.info("{} processing {}", this.getClass().getSimpleName(), event);
        this.context.publishEvent(new FillEvent(event.getSymbol(), event.getOrderType(), event.getQuantity()));
    }
}
