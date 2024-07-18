package atsys.backtesting.components.impl;

import atsys.backtesting.components.ExecutionManager;
import atsys.backtesting.engine.events.FillEvent;
import atsys.backtesting.model.Order;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "SimulatedExecutionManager")
public class SimulatedExecutionManager extends ExecutionManager {

    @Override
    @SneakyThrows
    public void processOrder(Order order) {
        log.info("processing {}", order);
        Long processedQty = order.getInitialQty();

        order.place();
        order.fill(order.getInitialQty());
        this.context.publishEvent(new FillEvent(order, processedQty));
    }
}
