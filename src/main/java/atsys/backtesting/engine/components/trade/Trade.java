package atsys.backtesting.engine.components.trade;

import atsys.backtesting.engine.components.asset.Instrument;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
public class Trade {

    private final String exchangeTradeId;
    private final String exchangeOrderId;
    private final Instrument instrument;
    private final long quantity;
    private final double price;
    private final Instant timestamp;
    private final String exchangeMessage;

    // Constructor managed by the Builder
    Trade(
            String exchangeTradeId,
            String exchangeOrderId,
            Instrument instrument,
            long quantity,
            double price,
            String exchangeMessage) {
        this.exchangeTradeId = exchangeTradeId;
        this.exchangeOrderId = exchangeOrderId;
        this.instrument = instrument;
        this.quantity = quantity;
        this.price = price;
        this.exchangeMessage = exchangeMessage;
        this.timestamp = Instant.now();
    }

    @Override
    public String toString() {
        return String.format("%s{%s, qty=%d, price=%.2f}",
                this.getClass().getSimpleName(),
                instrument.getName(),
                quantity,
                price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return Objects.equals(exchangeTradeId, trade.exchangeTradeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeTradeId);
    }
}