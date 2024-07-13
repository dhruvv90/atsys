package atsys.backtesting.components.impl;

import atsys.backtesting.components.ExecutionManager;
import atsys.backtesting.engine.events.FillEvent;
import atsys.backtesting.engine.events.OrderEvent;
import atsys.backtesting.model.Order;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Execution")
public class SimulatedExecutionManager extends ExecutionManager {

    @Override
    public void processOrder(OrderEvent event) {
        Order order = event.getOrder();
        log.info("{} processing {}", this.getClass().getSimpleName(), event);
        this.context.publishEvent(new FillEvent(order, order.getQuantity())); // assuming 100% fill..
    }
}
