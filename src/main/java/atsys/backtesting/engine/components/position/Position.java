package atsys.backtesting.engine.components.position;

import atsys.backtesting.engine.components.asset.Instrument;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Position {
    private final Instrument instrument;

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

    Position(Instrument instrument){
        this.instrument = instrument;
    }
}
