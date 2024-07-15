package atsys.backtesting.components.impl;

import atsys.backtesting.components.PortfolioManager;
import atsys.backtesting.engine.events.FillEvent;
import atsys.backtesting.engine.events.OrderEvent;
import atsys.backtesting.engine.events.SignalEvent;
import atsys.backtesting.model.OrderType;
import atsys.backtesting.model.Signal;
import atsys.backtesting.model.SignalType;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "NoobPortfolioManager")
public class NoobPortfolioManager extends PortfolioManager {

    private static final long orderQty = 5L;

    @Override
    public void onSignal(SignalEvent event) {
        Signal signal = event.getSignal();
        Long currPos = context.getPositionCount(signal.getSymbol());
        log.info("processing {}. current qty : {}", event, currPos);

        OrderEvent orderEvent = null;
        if(currPos <= 0 && signal.getSignalType() == SignalType.BUY){
            orderEvent = new OrderEvent(signal.getSymbol(), OrderType.BUY, orderQty);
        }
        else if(currPos > 0 && signal.getSignalType() == SignalType.SELL){
            orderEvent = new OrderEvent(signal.getSymbol(), OrderType.SELL, -1 * orderQty);
        }

        if(orderEvent != null){
            context.recordOrder(orderEvent.getOrder());
            context.publishEvent(orderEvent);
        }
    }

    @Override
    public void onFill(FillEvent event) {
        log.info("processing {}", event);
        context.recordOrder(event.getOrder());
    }
}
