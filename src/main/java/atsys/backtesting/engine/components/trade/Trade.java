package atsys.backtesting.engine.components.trade;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.utils.Decimal;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Trade {

    private final String tradeId;
    private final String orderId;
    private final Instrument instrument;
    private final long quantity;
    private final Decimal avgPrice;
    private final TradeType tradeType;

    // Constructor managed by the Builder
    public Trade(
            String tradeId,
            String orderId,
            Instrument instrument,
            long quantity,
            Decimal avgPrice,
            TradeType tradeType) {
        this.tradeId = tradeId;
        this.orderId = orderId;
        this.instrument = instrument;
        this.quantity = quantity;
        this.avgPrice = avgPrice;
        this.tradeType= tradeType;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "{"
                + "id=" + tradeId
                + ",sym=" + instrument
                + ",type=" + tradeType
                + ",qty=" + quantity
                + ",price=" + avgPrice
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