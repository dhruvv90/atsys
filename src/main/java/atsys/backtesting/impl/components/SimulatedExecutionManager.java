package atsys.backtesting.impl.components;

import atsys.backtesting.engine.components.ExecutionManager;
import atsys.backtesting.engine.order.Order;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "SimulatedExecutionManager")
public class SimulatedExecutionManager extends ExecutionManager {

    @Override
    @SneakyThrows
    public void processOrder(Order order) {
        log.info("tick {}, processing {}", context.getLastTick().getLastTradedPrice(), order);
        Long processedQty = order.getInitialQty();

        context.publishFill(order, processedQty);
    }
}
