package atsys.backtesting.engine.events;

import atsys.backtesting.engine.components.order.OrderFill;
import lombok.Getter;

@Getter
public class OrderFillEvent extends Event {
    private final OrderFill orderFill;

    public OrderFillEvent(OrderFill orderFill){
        this.orderFill = orderFill;
    }
}
