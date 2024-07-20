package atsys.backtesting.engine.events;

import atsys.backtesting.engine.order.Order;
import lombok.Getter;

@Getter
public class FillEvent extends Event {
    private final Order order;
    private final Long filledQty;

    public FillEvent(Order order, Long filledQty){
        this.order = order;
        this.filledQty = filledQty;
    }
}
