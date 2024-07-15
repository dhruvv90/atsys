package atsys.backtesting.model;

import atsys.backtesting.engine.OrderIdGenerator;
import atsys.backtesting.exception.InvalidOrderStateTransition;
import lombok.Getter;


@Getter
public class Order {

    private final long id;
    private final String symbol;
    private final OrderType orderType;
    private final Long initialQty;
    private Long currQty = 0L;
    private OrderStatus orderStatus;

    public Order(String symbol, OrderType orderType, Long initialQty) {
        this.symbol = symbol;
        this.orderType = orderType;
        this.initialQty = initialQty;
        this.orderStatus = OrderStatus.CREATED;
        this.id = OrderIdGenerator.generateId();
    }

    public void place() throws InvalidOrderStateTransition {
        if(orderStatus != OrderStatus.CREATED){
            throw new InvalidOrderStateTransition();
        }
        this.orderStatus = OrderStatus.OPEN;
    }

    public void cancel() throws InvalidOrderStateTransition {
        if(orderStatus != OrderStatus.OPEN){
            throw new InvalidOrderStateTransition();
        }
        this.orderStatus = OrderStatus.CANCELLED;
    }

    public void reject() throws InvalidOrderStateTransition {
        if(orderStatus != OrderStatus.OPEN){
            throw new InvalidOrderStateTransition();
        }
        this.orderStatus = OrderStatus.REJECTED;
    }

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
                "(", symbol, ",", orderType.toString(), ", ", initialQty.toString(), ", " + orderStatus + ")");
    }
}
