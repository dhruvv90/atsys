package atsys.backtesting.components;

import atsys.backtesting.components.order.Order;

public abstract class ExecutionManager extends LifecycleManager {
    public abstract void processOrder(Order order);
}
