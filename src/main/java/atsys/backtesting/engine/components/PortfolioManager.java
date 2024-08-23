package atsys.backtesting.engine.components;

import atsys.backtesting.engine.components.order.OrderFill;
import atsys.backtesting.engine.components.order.OrderService;
import atsys.backtesting.engine.components.signal.Signal;
import lombok.Setter;

@Setter
public abstract class PortfolioManager extends LifecycleManager {
    protected OrderService orderService;

    public abstract void onSignal(Signal signal);
    public abstract void onFill(OrderFill fill);

}
