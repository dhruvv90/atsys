package atsys.backtesting.impl.components;

import atsys.backtesting.engine.components.ExecutionManager;
import atsys.backtesting.engine.components.order.Order;
import atsys.backtesting.engine.components.order.OrderFill;
import atsys.backtesting.engine.components.order.OrderFillStatus;
import atsys.backtesting.engine.components.order.OrderSide;
import atsys.backtesting.engine.components.trade.Trade;
import atsys.backtesting.engine.components.trade.TradeType;
import atsys.utils.Decimal;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j(topic = "SimulatedExecutionManager")
public class SimulatedExecutionManager extends ExecutionManager {

    @Override
    public void processOrder(Order order) {
        log.info("tick {}, processing {}", context.getLastTick().getLastTradedPrice(), order);
        Decimal ltp = context.getLastTick().getLastTradedPrice();

        OrderFill fill = new OrderFill(order.getOrderId(), OrderFillStatus.SUCCESS, order.getOrderId());
        context.publishFill(fill);

        if(order.getTotalQty() == 1){
            publishTrade(order, order.getTotalQty(), ltp);
        }
        else{
            publishTrade(order, order.getTotalQty()-1, ltp);
            publishTrade(order, 1, ltp.multiply(1.12));
        }
    }

    private void publishTrade(Order order, long quantity, Decimal price) {
        TradeType type = order.getOrderSide().equals(OrderSide.BUY) ? TradeType.BUY : TradeType.SELL;
        Trade trade = new Trade(UUID.randomUUID().toString(), order.getOrderId(), order.getInstrument(), quantity, price, type);
        context.publishTrade(trade);
    }
}
