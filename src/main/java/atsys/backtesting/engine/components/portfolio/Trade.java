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

    private final String id;
    private final String orderId;
    private final Instrument instrument;
    private final long quantity;
    private final Decimal executedPrice;
    private final boolean isBuy;

    // Constructor managed by the Builder
    Trade(
            String id,
            String orderId,
            Instrument instrument,
            long quantity,
            Decimal executedPrice) {
        this.id = id;
        this.orderId = orderId;
        this.instrument = instrument;
        this.quantity = quantity;
        this.executedPrice = executedPrice;
        this.isBuy = this.quantity > 0;
    }

    public Decimal getTradeValue(){
        return executedPrice.multiply(quantity);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + " {"
                + "id="+id
                + ", "+instrument
                + ", " + StringUtils.getDirectionText(isBuy)
                + ", qty=" + quantity
                + ", price=" + executedPrice
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return Objects.equals(id, trade.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}