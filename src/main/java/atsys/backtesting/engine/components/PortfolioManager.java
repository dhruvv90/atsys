package atsys.backtesting.engine.components;

import atsys.backtesting.engine.components.order.Order;
import atsys.backtesting.engine.components.signal.Signal;

public abstract class PortfolioManager extends LifecycleManager {

    public abstract void onSignal(Signal signal);
    public abstract void onFill(Order order);

}
