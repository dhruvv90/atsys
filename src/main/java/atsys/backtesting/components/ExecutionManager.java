package atsys.backtesting.components;

import atsys.backtesting.engine.events.OrderEvent;

public interface ExecutionManager extends LifecycleManager {

    void processOrder(OrderEvent event);
}
