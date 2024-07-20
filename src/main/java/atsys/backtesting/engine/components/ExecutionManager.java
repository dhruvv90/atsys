package atsys.backtesting.engine.components;

import atsys.backtesting.engine.components.order.Order;
import atsys.backtesting.engine.exception.InvalidOrderStateTransitionException;

public abstract class ExecutionManager extends LifecycleManager {
    public abstract void processOrder(Order order) throws InvalidOrderStateTransitionException;
}
