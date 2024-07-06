package atsys.backtesting.components.execution;

import atsys.backtesting.BacktestingContext;
import atsys.backtesting.engine.events.OrderEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimulatedExecutionManager implements ExecutionManager {

    @Override
    public void onInit(BacktestingContext context) {
        log.info("Initiating {}", this.getClass().getSimpleName());
    }

    @Override
    public void onComplete() {
        log.info("Ending {}", this.getClass().getSimpleName());
    }

    @Override
    public void processOrder(OrderEvent event) {
        log.info("{} processing OrderEvent", this.getClass().getSimpleName());
    }
}
