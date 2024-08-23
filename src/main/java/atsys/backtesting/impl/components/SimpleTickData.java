package atsys.backtesting.impl.components;

import atsys.backtesting.engine.components.TickData;
import atsys.backtesting.engine.components.asset.Instrument;
import atsys.utils.Decimal;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SimpleTickData implements TickData {
    private Instant tickTimestamp;
    private Instant lastTradedTime;
    private int lastTradedQuantity;
    private Decimal lastTradedPrice;
    private Instrument instrument;
}
