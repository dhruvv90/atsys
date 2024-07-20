package atsys.backtesting.impl.components;

import atsys.backtesting.engine.components.TickData;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SimpleTickData implements TickData {
    private Instant tickTimestamp;
    private Instant lastTradedTime;
    private double lastTradedQuantity;
    private double lastTradedPrice;
    private String symbol;
    private String instrumentName;
}
