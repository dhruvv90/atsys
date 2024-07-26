package atsys.backtesting.engine.components;

import atsys.backtesting.engine.components.asset.Instrument;

import java.time.Instant;

public interface TickData {
    Instant getTickTimestamp();
    double getLastTradedPrice();
    Instrument getInstrument();
}
