package atsys.backtesting.components.strategy;

import atsys.backtesting.engine.BacktestingContext;
import atsys.backtesting.components.LifecycleManager;
import atsys.backtesting.engine.events.TickEvent;

public interface Strategy extends LifecycleManager {

    void onInit(BacktestingContext context);
    void handleTick(TickEvent event);
    void onComplete();

}
