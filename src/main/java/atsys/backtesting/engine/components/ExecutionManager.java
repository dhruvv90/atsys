package atsys.backtesting.engine.components;

import atsys.backtesting.engine.components.order.Order;
import atsys.backtesting.engine.components.portfolio.TradeService;
import atsys.backtesting.engine.exception.InvalidOrderStateTransitionException;
import lombok.Setter;

@Setter
public abstract class ExecutionManager extends LifecycleManager {
    protected TradeService tradeService;

    public abstract void processOrder(Order order) throws InvalidOrderStateTransitionException;
}
