package atsys.backtesting.components.execution;

import atsys.backtesting.components.LifecycleManager;
import atsys.backtesting.engine.events.OrderEvent;

public interface ExecutionManager extends LifecycleManager {

    void processOrder(OrderEvent event);
}
