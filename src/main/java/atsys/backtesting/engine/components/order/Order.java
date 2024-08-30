package atsys.backtesting.engine.components.order;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.backtesting.engine.components.portfolio.Trade;
import atsys.utils.Decimal;
import atsys.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


@Getter
public class Order {

    private final String id;
    private final String signalId;

    @Setter
    private String exchangeOrderId;
    private final Instrument instrument;
    private final OrderType orderType;
    private final long totalQty;
    private long filledQty;

    @Setter
    private Decimal avgPrice = Decimal.ZERO;

    @Setter
    private OrderStatus orderStatus;
    private final Instant createdAt;
    private final boolean isBuy;
    private final OrderValidity validity;
    private final List<Trade> trades = new LinkedList<>();

    Order(String id, String signalId, Instrument instrument, long totalQty) {
        this.instrument = instrument;
        this.totalQty = totalQty;
        this.isBuy = totalQty > 0;
        this.createdAt = Instant.now();
        this.id = id;
        this.signalId = signalId;
        validity = OrderValidity.DAY;
        orderStatus = OrderStatus.CREATED_INTERNAL;
        orderType = OrderType.MARKET;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + " {"
                + "id="+id
                + ", "+ instrument
                + StringUtils.getDirectionText(isBuy)
                + ", " + instrument
                + ", qty=" + totalQty
                + ", filled=" + filledQty
                + ", " + orderStatus
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void addTrade(Trade trade) {
        avgPrice = avgPrice.multiply(filledQty).add(trade.getTradeValue())
                .divide((double) filledQty + trade.getQuantity());
        filledQty += trade.getQuantity();
        if (filledQty == totalQty) {
            this.orderStatus = OrderStatus.COMPLETED;
        } else {
            this.orderStatus = OrderStatus.COMPLETED_PARTIAL;
        }
        trades.add(trade);
    }
}
