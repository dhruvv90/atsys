package atsys.backtesting.impl.components;

import atsys.backtesting.engine.components.ExecutionManager;
import atsys.backtesting.engine.components.order.Order;
import atsys.backtesting.engine.components.order.OrderFill;
import atsys.backtesting.engine.components.order.OrderFillStatus;
import atsys.backtesting.engine.components.order.OrderSide;
import atsys.backtesting.engine.components.trade.Trade;
import atsys.backtesting.engine.components.trade.TradeType;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j(topic = "SimulatedExecutionManager")
public class SimulatedExecutionManager extends ExecutionManager {

    @Override
    public void processOrder(Order order) {
        log.info("tick {}, processing {}", context.getLastTick().getLastTradedPrice(), order);
        Long filledQty = order.getQuantity();
        Double filledPrice = context.getLastTick().getLastTradedPrice();

        OrderFill fill = new OrderFill(order.getOrderId(), OrderFillStatus.SUCCESS, order.getOrderId());
        context.publishFill(fill);

        Trade trade = createTrade(order);
        context.publishTrade(trade);
    }

    private Trade createTrade(Order order){
        TradeType type = order.getOrderSide().equals(OrderSide.BUY) ? TradeType.BUY : TradeType.SELL;

        return new Trade(UUID.randomUUID().toString(), order.getOrderId(), order.getInstrument(), order.getQuantity(),
                context.getLastTick().getLastTradedPrice(), type);
    }
}
