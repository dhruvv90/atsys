package atsys.backtesting.engine.components;

import atsys.backtesting.engine.order.Order;

public abstract class ExecutionManager extends LifecycleManager {
    public abstract void processOrder(Order order);
}
