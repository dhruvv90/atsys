package atsys.backtesting.components;

import atsys.backtesting.model.Order;

public abstract class ExecutionManager extends LifecycleManager {

    public abstract void processOrder(Order order);
}
