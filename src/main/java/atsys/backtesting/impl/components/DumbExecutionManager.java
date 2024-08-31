package atsys.backtesting.impl.components;

import atsys.backtesting.engine.components.ExecutionManager;
import atsys.backtesting.engine.components.order.Order;
import atsys.backtesting.engine.components.order.OrderFill;
import atsys.backtesting.engine.components.order.OrderFillStatus;
import atsys.backtesting.engine.components.portfolio.Trade;
import atsys.utils.Decimal;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "SimulatedExecutionManager")
public class DumbExecutionManager extends ExecutionManager {

    private static final double SLIPPAGE_PERCENTAGE = 0.1;
    private static final int LATENCY_MS = 0;


    @Override
    public void processOrder(Order order) {
        log.info("tick {}, processing {}", context.getLastTick().getLastTradedPrice(), order);
        Decimal ltp = context.getLastTick().getLastTradedPrice();

        OrderFill fill = new OrderFill(order.getId(), OrderFillStatus.SUCCESS, order.getId());
        context.publishFill(fill);

        if(order.getTotalQty() == 1){
            publishTrade(order, order.getTotalQty(), ltp);
        }
        else{
            publishTrade(order, order.getTotalQty()-1, ltp);
            publishTrade(order, 1, ltp.multiply(1.12));
        }
    }

    @SneakyThrows
    private void publishTrade(Order order, long quantity, Decimal price) {
        Thread.sleep(LATENCY_MS);
        // apply negative slippage..
        Decimal adjPrice;
        if(order.isBuy()){
            adjPrice = price.multiply(1 + (SLIPPAGE_PERCENTAGE/100.0));
        }
        else{
            adjPrice = price.multiply(1 - (SLIPPAGE_PERCENTAGE/100.0));
        }
        Trade trade = tradeService.createTrade(order.getId(), order.getInstrument(), quantity, adjPrice);
        context.publishTrade(trade);
    }
}
