package atsys.backtesting.engine.components.order;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.backtesting.engine.exception.InvalidParameterException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class OrderService {
    private final AtomicLong counter;
    private final List<Order> orders;

    public OrderService() {
        this.orders = new ArrayList<>();
        this.counter = new AtomicLong();
    }

    private long generateId() {
        return counter.incrementAndGet();
    }

    @SneakyThrows
    public Order createOrder(Instrument instrument, Long quantity) {
        if(quantity == 0){
            throw new InvalidParameterException("Invalid quantity: 0");
        }
        long id = generateId();

        OrderType orderType = quantity > 0 ? OrderType.BUY : OrderType.SELL;
        Order order = new Order(id, instrument, orderType, quantity);
        orders.add(order);
        return order;
    }

    public void updateOrderSuccess(Order order, Long fillQty, Double fillPrice) {
        OrderState state = order.getOrderState();
        if (state == OrderState.CREATED) {
            order.setFilledQty(fillQty);
            order.setAvgExecutedPrice(fillPrice);

        } else if (state == OrderState.OPEN) {
            Long currQty = order.getFilledQty();
            Double currPrice = order.getAvgExecutedPrice();

            double totalValue = currQty * currPrice + fillQty * fillPrice;
            long totalFilledQty = currQty + fillQty;

            order.setFilledQty(totalFilledQty);
            order.setAvgExecutedPrice(totalValue / totalFilledQty);

        } else {
            log.warn("Order : " + order.getId() + " is not Open or new. Already actioned");
            return;
        }

        order.setLastExchangeTime(Instant.now());
        if (order.getTotalQty().equals(order.getFilledQty())) {
            order.setOrderState(OrderState.COMPLETED);
        } else {
            order.setOrderState(OrderState.OPEN);
        }
    }

//    public void onOrderCancel(Order order) throws InvalidOrderStateTransitionException {
//        if(order.getOrderState() != OrderState.OPEN){
//            throw new InvalidOrderStateTransitionException();
//        }
//        order.setOrderState(OrderState.CANCELLED);
//    }
//
//    public void onOrderReject(Order order) throws InvalidOrderStateTransitionException {
//        if(order.getOrderState() != OrderState.OPEN){
//            throw new InvalidOrderStateTransitionException();
//        }
//        order.setOrderState(OrderState.REJECTED);
//    }
}
