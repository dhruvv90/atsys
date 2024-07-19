package atsys.backtesting.components;

import atsys.backtesting.components.order.Order;
import atsys.backtesting.model.Signal;

public abstract class PortfolioManager extends LifecycleManager {

    public abstract void onSignal(Signal signal);
    public abstract void onFill(Order order);

}
