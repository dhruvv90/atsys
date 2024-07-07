package atsys.backtesting.components.impl;

import atsys.backtesting.engine.BacktestingContext;
import atsys.backtesting.components.ExecutionManager;
import atsys.backtesting.engine.events.FillEvent;
import atsys.backtesting.engine.events.OrderEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimulatedExecutionManager implements ExecutionManager {

    private BacktestingContext context;

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
    public void processOrder(OrderEvent event) {
        log.info("{} processing OrderEvent", this.getClass().getSimpleName());
        this.context.publishEvent(new FillEvent());
    }
}
