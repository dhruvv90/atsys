package atsys.backtesting.engine.events;

import atsys.backtesting.engine.components.portfolio.Trade;
import lombok.Getter;

@Getter
public class TradeEvent extends Event {
    private final Trade trade;

    public TradeEvent(Trade trade){
        this.trade = trade;
    }
}
