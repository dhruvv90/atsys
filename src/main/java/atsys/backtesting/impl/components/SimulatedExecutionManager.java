package atsys.backtesting.impl.components;

import atsys.backtesting.engine.components.ExecutionManager;
import atsys.backtesting.engine.components.order.Order;
import atsys.backtesting.engine.components.order.OrderFill;
import atsys.backtesting.engine.components.order.OrderFillStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "SimulatedExecutionManager")
public class SimulatedExecutionManager extends ExecutionManager {

    @Override
    public void processOrder(Order order) {
        log.info("tick {}, processing {}", context.getLastTick().getLastTradedPrice(), order);
        Long filledQty = order.getQuantity();
        Double filledPrice = context.getLastTick().getLastTradedPrice();

        OrderFill fill = new OrderFill(order.getOrderId(), OrderFillStatus.SUCCESS, order.getOrderId());
        context.publishFill(fill);
    }
}
