package atsys.backtesting.components;

import atsys.backtesting.engine.events.TickEvent;

public interface Strategy extends LifecycleManager {
    void handleTick(TickEvent event);
}
