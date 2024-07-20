package atsys.backtesting.impl.components;

import atsys.backtesting.engine.components.ExecutionManager;
import atsys.backtesting.engine.components.order.Order;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "SimulatedExecutionManager")
public class SimulatedExecutionManager extends ExecutionManager {

    @Override
    public void processOrder(Order order) {
        log.info("tick {}, processing {}", context.getLastTick().getLastTradedPrice(), order);
        Long processedQty = order.getInitialQty();

        context.publishFill(order, processedQty);
    }
}
