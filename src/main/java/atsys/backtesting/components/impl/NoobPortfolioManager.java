package atsys.backtesting.components.impl;

import atsys.backtesting.BacktestingContext;
import atsys.backtesting.components.PortfolioManager;
import atsys.backtesting.engine.events.OrderEvent;
import atsys.backtesting.engine.events.SignalEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoobPortfolioManager implements PortfolioManager {

    private BacktestingContext context;

    @Override
    public void onSignal(SignalEvent event) {
        log.info("{} processing Signal", this.getClass().getSimpleName());
        context.publishEvent(new OrderEvent());
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
}
