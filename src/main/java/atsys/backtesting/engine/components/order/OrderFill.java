package atsys.backtesting.engine.components.order;


import lombok.Getter;

@Getter
public class OrderFill {
    private final String orderId;
    private final OrderFillStatus orderFillStatus;
    private final String exchangeOrderId;

    public OrderFill(String orderId, OrderFillStatus orderFillStatus, String exchangeOrderId){
        this.orderId = orderId;
        this.orderFillStatus = orderFillStatus;
        this.exchangeOrderId = exchangeOrderId;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "{"
                + "orderId=" + orderId
                + ",status=" + orderFillStatus
                + "}";
    }
}
