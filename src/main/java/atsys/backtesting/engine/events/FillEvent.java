package atsys.backtesting.engine.events;

import atsys.backtesting.engine.components.order.OrderFill;
import lombok.Getter;

@Getter
public class FillEvent extends Event {
    private final OrderFill orderFill;

    public FillEvent(OrderFill orderFill){
        this.orderFill = orderFill;
    }
}
