package atsys.backtesting.engine.events;


import atsys.backtesting.model.OrderType;
import lombok.Getter;

@Getter
public class OrderEvent extends Event{

    private final Long quantity;
    private final String symbol;
    private final OrderType orderType;

    public OrderEvent(String symbol, OrderType ordertype, Long quantity){
        this.symbol = symbol;
        this.orderType = ordertype;
        this.quantity = quantity;
    }
}
