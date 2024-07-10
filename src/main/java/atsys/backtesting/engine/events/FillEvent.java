package atsys.backtesting.engine.events;

import atsys.backtesting.model.OrderType;
import lombok.Getter;

@Getter
public class FillEvent extends Event {
    private final Long filledQty;
    private final String symbol;
    private final OrderType orderType;

    public FillEvent(String symbol, OrderType ordertype, Long filledQty){
        this.symbol = symbol;
        this.orderType = ordertype;
        this.filledQty = filledQty;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +": (" + orderType + ", " + symbol + ", filled : " + filledQty + ")";
    }
}
