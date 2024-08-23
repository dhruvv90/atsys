package atsys.backtesting.engine.components.position;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.utils.Decimal;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Position {
    private final Instrument instrument;

    @Setter
    private long quantity;

    @Setter
    private Decimal averagePrice;

    @Setter
    private Decimal lastTradedPrice;

    @Setter
    private Decimal pnl;

    @Setter
    private Decimal percentageChange;

    Position(Instrument instrument){
        this.instrument = instrument;
    }
}
