package atsys.backtesting.components.strategy;

import atsys.backtesting.components.LifecycleManager;
import atsys.backtesting.engine.events.TickEvent;

public interface Strategy extends LifecycleManager {
    void handleTick(TickEvent event);
}
