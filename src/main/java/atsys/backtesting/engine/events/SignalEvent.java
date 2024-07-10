package atsys.backtesting.engine.events;

import atsys.backtesting.model.OrderType;
import lombok.Getter;

@Getter
public class SignalEvent extends Event {

    private final OrderType orderType;
    private final String symbol;

    public SignalEvent(String symbol, OrderType orderType){
        this.orderType = orderType;
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +": (" + orderType + ", " + symbol + ")";
    }
}
