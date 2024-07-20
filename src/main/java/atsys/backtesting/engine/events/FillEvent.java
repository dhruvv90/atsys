package atsys.backtesting.engine.events;

import atsys.backtesting.engine.components.order.Order;
import lombok.Getter;

@Getter
public class FillEvent extends Event {
    private final Order order;

    public FillEvent(Order order){
        this.order = order;
    }
}
