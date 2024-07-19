package atsys.backtesting.components.impl;

import atsys.backtesting.components.PortfolioManager;
import atsys.backtesting.components.order.Order;
import atsys.backtesting.components.order.OrderType;
import atsys.backtesting.model.Signal;
import atsys.backtesting.model.SignalType;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "NoobPortfolioManager")
public class NoobPortfolioManager extends PortfolioManager {


    @Override
    public void onSignal(Signal signal) {
        Long currPos = context.getPositionCount(signal.getSymbol());
        long orderQty = 5;

        if(currPos <= 0 && signal.getSignalType() == SignalType.BUY){
            context.publishOrder(signal.getSymbol(), OrderType.BUY, orderQty);
        }
        else if(currPos > 0 && signal.getSignalType() == SignalType.SELL){
            orderQty *= -1;
            context.publishOrder(signal.getSymbol(), OrderType.SELL, orderQty);
        }
        log.info("processing {}. currQty: {}, newOrder: {}", signal, currPos, orderQty);
    }

    @Override
    public void onFill(Order order) {
        log.info("processing order: {}, filled: {}", order, order.getCurrQty());
    }
}
