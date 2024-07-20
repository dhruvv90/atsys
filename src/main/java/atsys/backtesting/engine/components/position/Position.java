package atsys.backtesting.engine.components.position;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Position {
    private final String symbol;

    @Setter
    private long quantity;

    @Setter
    private double averagePrice;

    @Setter
    private double lastTradedPrice;

    @Setter
    private double pnl;

    @Setter
    private double percentageChange;

    Position(String symbol){
        this.symbol = symbol;
    }
}
