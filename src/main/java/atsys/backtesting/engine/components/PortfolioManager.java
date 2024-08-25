package atsys.backtesting.engine.components;

import atsys.backtesting.engine.components.order.OrderFill;
import atsys.backtesting.engine.components.order.OrderService;
import atsys.backtesting.engine.components.portfolio.PositionService;
import atsys.backtesting.engine.components.portfolio.Trade;
import atsys.backtesting.engine.components.signal.Signal;
import lombok.Setter;

@Setter
public abstract class PortfolioManager extends LifecycleManager {
    protected OrderService orderService;
    protected PositionService positionService;

    public abstract void onSignal(Signal signal);
    public abstract void onFill(OrderFill fill);
    public abstract void onTrade(Trade trade);

}
