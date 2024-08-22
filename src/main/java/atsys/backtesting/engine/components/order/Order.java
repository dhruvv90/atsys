package atsys.backtesting.engine.components.order;

import atsys.backtesting.engine.components.asset.Instrument;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
public class Order {

    private final String orderId;
    private final Instrument instrument;
    private final OrderType orderType = OrderType.MARKET;
    private final long quantity;
    //    private double avgExecutedPrice;
    private OrderStatus orderStatus;
    private final Instant createdAt;
    private final OrderSide orderSide;
    private final OrderValidity validity = OrderValidity.DAY;
//    private final String traderId;
//    private final String brokerId;

    Order(Instrument instrument, long quantity, OrderSide orderSide) {
        this.instrument = instrument;
        this.quantity = quantity;
        this.orderSide = orderSide;
        this.createdAt = Instant.now();
        this.orderId = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                String.join(",", "orderId" + orderId, "instrument: " + instrument, "side: " + orderSide) + "}";
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
