package atsys.backtesting.impl.components;

import atsys.backtesting.engine.components.PortfolioManager;
import atsys.backtesting.engine.components.order.Order;
import atsys.backtesting.engine.components.order.OrderType;
import atsys.backtesting.engine.components.position.Position;
import atsys.backtesting.engine.components.signal.Signal;
import atsys.backtesting.engine.components.signal.SignalType;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "NoobPortfolioManager")
public class NoobPortfolioManager extends PortfolioManager {


    @Override
    public void onSignal(Signal signal) {
        long currPos = context.getPosition(signal.getSymbol()).map(Position::getQuantity).orElse(0L);
        long orderQty = 5;

        if(currPos <= 0 && signal.getSignalType() == SignalType.BUY){
            context.publishOrder(signal.getSymbol(), OrderType.BUY, orderQty);
        }
        else if(currPos > 0 && signal.getSignalType() == SignalType.SELL){
            orderQty *= -1;
            context.publishOrder(signal.getSymbol(), OrderType.SELL, orderQty);
        }
        log.info("tick: {}, processing {}. currQty: {}, newOrder: {}", context.getLastTick().getLastTradedPrice(), signal, currPos, orderQty);
    }

    @Override
    public void onFill(Order order) {
        log.info("tick: {}, processing order: {}, filled: {}", context.getLastTick().getLastTradedPrice(), order, order.getCurrQty());
    }
}
