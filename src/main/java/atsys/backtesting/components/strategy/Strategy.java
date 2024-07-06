package atsys.backtesting.components.strategy;

import atsys.backtesting.BacktestingContext;
import atsys.backtesting.components.LifecycleManager;
import atsys.backtesting.engine.event.TickEvent;

public interface Strategy extends LifecycleManager {

    void onInit(BacktestingContext context);
    void handleTick(TickEvent event);
    void onComplete();

}
