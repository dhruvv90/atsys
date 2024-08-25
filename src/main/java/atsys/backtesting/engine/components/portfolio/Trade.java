package atsys.backtesting.engine.components.portfolio;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.utils.Decimal;
import atsys.utils.StringUtils;
import lombok.Getter;

import java.util.Objects;

/**
 * Immutable class
 */
@Getter
public final class Trade {

    private final String tradeId;
    private final String orderId;
    private final Instrument instrument;
    private final long quantity;
    private final Decimal avgPrice;
    private final boolean isBuy;
    private final Decimal tradeValue;

    // Constructor managed by the Builder
    public Trade(
            String tradeId,
            String orderId,
            Instrument instrument,
            long quantity,
            Decimal avgPrice,
            boolean isBuy) {
        this.tradeId = tradeId;
        this.orderId = orderId;
        this.instrument = instrument;
        this.quantity = quantity;
        this.avgPrice = avgPrice;
        this.isBuy = isBuy;
        this.tradeValue = avgPrice.multiply(quantity);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + " {"
                + instrument
                + ", " + StringUtils.getDirectionText(isBuy)
                + ", qty=" + quantity
                + ", price=" + avgPrice
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return Objects.equals(tradeId, trade.tradeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeId);
    }
}