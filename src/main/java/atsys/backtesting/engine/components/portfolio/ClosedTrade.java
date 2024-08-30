package atsys.backtesting.engine.components.portfolio;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.utils.Decimal;
import lombok.Getter;

@Getter
public class ClosedTrade {
    private final String id;
    private final Instrument instrument;
    private final long quantity;
    private final Decimal buyPrice;
    private final Decimal sellPrice;

    public ClosedTrade(String id, Instrument instrument, long quantity, Decimal buyPrice, Decimal sellPrice){
        this.id = id;
        this.instrument = instrument;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }
}
