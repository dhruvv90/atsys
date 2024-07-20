package atsys.backtesting.engine.components;

import atsys.backtesting.engine.BacktestingContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Represents a resource , which is a reusable and runnable component.
 * It provides lifecycle hooks for multiple stages, to carry out actions at certain states
 */
@Slf4j(topic = "Setup")
public abstract class LifecycleManager {
    protected BacktestingContext context;

    public void onInit(BacktestingContext context) {
        this.context = context;
        log.info("Initiating : " + this.getClass().getSimpleName());
    }

    public void onComplete(){
        log.info("Terminating : " + this.getClass().getSimpleName());
    }
}
