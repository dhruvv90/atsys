package atsys.backtesting.model;

import atsys.backtesting.engine.OrderIdGenerator;
import atsys.backtesting.exception.InvalidOrderStateTransition;
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
    private Long currQty = 0L;

    private final Long id;
    private final Instant createdAt;
    private final String symbol;
    private final OrderType orderType;
    private final Long initialQty;
    private OrderStatus orderStatus;

    public Order(String symbol, OrderType orderType, Long initialQty) {
        this.symbol = symbol;
        this.orderType = orderType;
        this.initialQty = initialQty;
        this.orderStatus = OrderStatus.CREATED;
        this.id = OrderIdGenerator.generateId();
        this.createdAt = Instant.now();
    }

    // to remove
    public void place() throws InvalidOrderStateTransition {
        if(orderStatus != OrderStatus.CREATED){
            throw new InvalidOrderStateTransition();
        }
        this.orderStatus = OrderStatus.OPEN;
    }

    // to remove..
    public void cancel() throws InvalidOrderStateTransition {
        if(orderStatus != OrderStatus.OPEN){
            throw new InvalidOrderStateTransition();
        }
        this.orderStatus = OrderStatus.CANCELLED;
    }

    // to remove
    public void reject() throws InvalidOrderStateTransition {
        if(orderStatus != OrderStatus.OPEN){
            throw new InvalidOrderStateTransition();
        }
        this.orderStatus = OrderStatus.REJECTED;
    }

    // to remove
    public void fill(Long fillQty) throws InvalidOrderStateTransition {
        if(orderStatus != OrderStatus.OPEN){
            throw new InvalidOrderStateTransition();
        }
        currQty = fillQty;
        if(fillQty.equals(initialQty)){
            this.orderStatus = OrderStatus.COMPLETED;
        }
    }

    @Override
    public String toString() {
        return String.join("", this.getClass().getSimpleName(),
                "(", String.valueOf(id), " ", symbol, ",", orderType.toString(), ", ", initialQty.toString(), ", " + orderStatus + ")");
    }
}
