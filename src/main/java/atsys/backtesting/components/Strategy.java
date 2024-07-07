package atsys.backtesting.components;

import atsys.backtesting.model.TickData;

public interface Strategy extends LifecycleManager {
    void handleTick(TickData tickData);
}
