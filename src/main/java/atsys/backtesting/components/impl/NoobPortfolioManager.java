package atsys.backtesting.components.impl;

import atsys.backtesting.engine.BacktestingContext;
import atsys.backtesting.components.PortfolioManager;
import atsys.backtesting.engine.events.FillEvent;
import atsys.backtesting.engine.events.OrderEvent;
import atsys.backtesting.engine.events.SignalEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "PortfolioManager")
public class NoobPortfolioManager implements PortfolioManager {

    private BacktestingContext context;
    private final Long orderQty = 5L;

    @Override
    public void onSignal(SignalEvent event) {
        log.info("{} processing Signal : {}. Current qty : {}", this.getClass().getSimpleName(), event, context.getPositionCount(event.getSymbol()));
        context.publishEvent(new OrderEvent(event.getSymbol(), event.getOrderType(), orderQty));
    }

    @Override
    public void onInit(BacktestingContext context) {
        this.context = context;
        log.info("Initiating {}", this.getClass().getSimpleName());
    }

    @Override
    public void onComplete() {
        log.info("Ending {}", this.getClass().getSimpleName());
    }

    @Override
    public void onFill(FillEvent event) {
        log.info("{} processing Fill", this.getClass().getSimpleName());

        String symbol = event.getSymbol();
        context.recordPosition(symbol, event.getFilledQty());
    }
}
