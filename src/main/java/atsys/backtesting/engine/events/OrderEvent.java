package atsys.backtesting.engine.events;


import atsys.backtesting.engine.components.order.Order;
import lombok.Getter;

@Getter
public class OrderEvent extends Event{

    private final Order order;

    public OrderEvent(Order order){
        this.order = order;
    }
}
