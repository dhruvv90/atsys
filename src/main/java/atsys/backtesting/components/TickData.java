package atsys.backtesting.components;

import java.time.Instant;

public interface TickData {
    Instant getTickTimestamp();
    double getLastTradedPrice();
    String getSymbol();
}
