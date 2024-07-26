package atsys.backtesting.engine.components.order;

import atsys.backtesting.engine.components.asset.Instrument;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
public class Order {
    @Setter
    private Long exchangeId;
    @Setter
    private Instant lastExchangeTime;
    @Setter
    private String exchangeMessage;
    @Setter
    private Double avgExecutedPrice;
    @Setter
    private Long filledQty;
    @Setter
    private OrderState orderState;

    private final Long id;
    private final Instant createdAt;
    private final Instrument instrument;
    private final OrderType orderType;
    private final Long totalQty;

    Order(Long id, Instrument instrument, OrderType orderType, Long totalQty) {
        this.instrument = instrument;
        this.orderType = orderType;
        this.totalQty = totalQty;
        this.orderState = OrderState.CREATED;
        this.id = id;
        this.createdAt = Instant.now();
    }

    @Override
    public String toString() {
        return
                this.getClass().getSimpleName()
                        + "(" +
                        String.join(",",
                                String.valueOf(id),
                                instrument.toString(),
                                orderType.toString(),
                                "init: " + totalQty,
                                "curr: " + filledQty,
                                orderState.toString())
                        + ")";
    }
}
