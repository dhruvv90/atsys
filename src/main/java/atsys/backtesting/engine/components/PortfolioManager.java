package atsys.backtesting.engine.components;

import atsys.backtesting.engine.order.Order;
import atsys.backtesting.engine.model.Signal;

public abstract class PortfolioManager extends LifecycleManager {

    public abstract void onSignal(Signal signal);
    public abstract void onFill(Order order);

}
