package atsys.backtesting.model;

import lombok.Getter;


@Getter
public class Order {
    private final String symbol;
    private final OrderType orderType;
    private final Long quantity;

    public Order(String symbol, OrderType orderType, Long quantity) {
        this.symbol = symbol;
        this.orderType = orderType;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.join(" ", this.getClass().getSimpleName(), "(", symbol, ",", orderType.toString(), ", ", quantity.toString(), ")");
    }
}
