package atsys.backtesting.engine.components.order;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.backtesting.engine.components.portfolio.Trade;
import atsys.utils.Decimal;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


@Getter
public class Order {

    private final String orderId;
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
    private final OrderSide orderSide;
    private final OrderValidity validity;
//    private final String traderId;
//    private final String brokerId;
    private final List<Trade> trades = new LinkedList<>();

    Order(String orderId, Instrument instrument, long totalQty, OrderSide orderSide) {
        this.instrument = instrument;
        this.totalQty = totalQty;
        this.orderSide = orderSide;
        this.createdAt = Instant.now();
        this.orderId = orderId;
        validity = OrderValidity.DAY;
        orderStatus = OrderStatus.CREATED_INTERNAL;
        orderType = OrderType.MARKET;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "{"
                + "id=" + orderId
                + ","+ orderSide
                + ",sym=" + instrument
                + ",totalQty=" + totalQty
                + ",filledQty=" + filledQty
                + ",status=" +orderStatus
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderId);
    }

    public void addTrade(Trade trade){
        avgPrice = avgPrice.multiply(filledQty).add(trade.getTradeValue())
                        .divide(filledQty + trade.getQuantity());
        filledQty += trade.getQuantity();
        if(filledQty == totalQty){
            this.orderStatus = OrderStatus.COMPLETED;
        }
        else{
            this.orderStatus = OrderStatus.COMPLETED_PARTIAL;
        }
        trades.add(trade);
    }
}
