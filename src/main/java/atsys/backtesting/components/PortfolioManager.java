package atsys.backtesting.components;

import atsys.backtesting.engine.events.OrderEvent;
import atsys.backtesting.model.Order;
import atsys.backtesting.model.OrderType;
import atsys.backtesting.model.Signal;

public abstract class PortfolioManager extends LifecycleManager {

    public abstract void onSignal(Signal signal);
    public abstract void onFill(Order order);

    private void publishOrder(String symbol, Long quantity, OrderType orderType){
        OrderEvent orderEvent = new OrderEvent(symbol, orderType, quantity);
        context.publishEvent(orderEvent);
        context.recordOrder(orderEvent.getOrder());
    }

    protected void publishBuyOrder(String symbol, Long quantity){
        publishOrder(symbol, quantity, OrderType.BUY);
    }

    protected void publishSellOrder(String symbol, Long quantity){
        publishOrder(symbol, -1 * quantity, OrderType.SELL);
    }
}
