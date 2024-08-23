package atsys.backtesting.engine.components;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.utils.Decimal;

import java.time.Instant;

public interface TickData {
    Instant getTickTimestamp();
    Decimal getLastTradedPrice();
    Instrument getInstrument();
}
