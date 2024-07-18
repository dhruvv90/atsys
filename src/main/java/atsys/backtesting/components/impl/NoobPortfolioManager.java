package atsys.backtesting.components.impl;

import atsys.backtesting.components.PortfolioManager;
import atsys.backtesting.model.Order;
import atsys.backtesting.model.Signal;
import atsys.backtesting.model.SignalType;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "NoobPortfolioManager")
public class NoobPortfolioManager extends PortfolioManager {


    @Override
    public void onSignal(Signal signal) {
        Long currPos = context.getPositionCount(signal.getSymbol());

        if(currPos <= 0 && signal.getSignalType() == SignalType.BUY){
            publishBuyOrder(signal.getSymbol(), 5L);
        }
        else if(currPos > 0 && signal.getSignalType() == SignalType.SELL){
            publishSellOrder(signal.getSymbol(), 5L);
        }
        log.info("processing {}. currQty: {}, newOrder: {}", signal, currPos, 5);
    }

    @Override
    public void onFill(Order order) {
        log.info("processing order: {}, filled: {}", order, order.getCurrQty());
        context.recordOrder(order);
    }
}
