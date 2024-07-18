package atsys.backtesting.engine.events;


import atsys.backtesting.model.Order;
import atsys.backtesting.model.OrderType;
import lombok.Getter;

@Getter
public class OrderEvent extends Event{

    private final Order order;

    public OrderEvent(String symbol, OrderType ordertype, Long quantity){
        this.order = new Order(symbol, ordertype, quantity);
    }
}
