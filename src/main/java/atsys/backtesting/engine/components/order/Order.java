package atsys.backtesting.engine.components.order;

import atsys.backtesting.engine.components.asset.Instrument;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;


@Getter
public class Order {

    private final String orderId;
    @Setter
    private String exchangeOrderId;
    private final Instrument instrument;
    private final OrderType orderType = OrderType.MARKET;
    private final long quantity;
    //    private double avgExecutedPrice;
    @Setter
    private OrderStatus orderStatus = OrderStatus.CREATED;
    private final Instant createdAt;
    private final OrderSide orderSide;
    private final OrderValidity validity = OrderValidity.DAY;
//    private final String traderId;
//    private final String brokerId;

    Order(String orderId, Instrument instrument, long quantity, OrderSide orderSide) {
        this.instrument = instrument;
        this.quantity = quantity;
        this.orderSide = orderSide;
        this.createdAt = Instant.now();
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "{"
                + "id=" + orderId
                + ",sym=" + instrument
                + ",qty=" + quantity
                + ","+ orderSide
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
}
