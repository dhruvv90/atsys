package atsys.backtesting.components;

import atsys.backtesting.engine.events.OrderEvent;

public abstract class ExecutionManager extends LifecycleManager {

    public abstract void processOrder(OrderEvent event);
}
