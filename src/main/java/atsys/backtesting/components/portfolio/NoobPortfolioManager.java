package atsys.backtesting.components.portfolio;

import atsys.backtesting.BacktestingContext;
import atsys.backtesting.engine.events.SignalEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoobPortfolioManager implements PortfolioManager {

    @Override
    public void onSignal(SignalEvent event) {
        log.info("{} processing Signal", this.getClass().getSimpleName());
    }

    @Override
    public void onInit(BacktestingContext context) {
        log.info("Initiating {}", this.getClass().getSimpleName());
    }

    @Override
    public void onComplete() {
        log.info("Ending {}", this.getClass().getSimpleName());
    }
}
