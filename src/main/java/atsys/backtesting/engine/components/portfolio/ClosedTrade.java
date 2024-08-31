package atsys.backtesting.engine.components.portfolio;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.utils.Decimal;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

@Getter
public class ClosedTrade {
    private final Instrument instrument;
    private final long quantity;
    private final Decimal buyPrice;
    private final Decimal sellPrice;
    private final Instant timestamp;
    private final Decimal pnl;

    public ClosedTrade(Instrument instrument, long quantity, Decimal buyPrice, Decimal sellPrice) {
        this.instrument = instrument;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.timestamp = Instant.now();
        this.pnl = this.sellPrice.subtract(this.buyPrice).multiply(this.quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClosedTrade that = (ClosedTrade) o;
        return quantity == that.quantity  && Objects.equals(instrument, that.instrument) && Objects.equals(buyPrice, that.buyPrice) && Objects.equals(sellPrice, that.sellPrice) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument, quantity, buyPrice, sellPrice, timestamp);
    }
}
