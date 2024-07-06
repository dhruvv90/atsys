package atsys.backtesting.components;

import atsys.backtesting.LifecycleManager;
import atsys.backtesting.core.event.TickEvent;

public interface Strategy extends LifecycleManager {

    void handleTick(TickEvent event);

}
