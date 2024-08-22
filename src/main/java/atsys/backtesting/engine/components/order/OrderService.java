package atsys.backtesting.engine.components.order;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.backtesting.engine.exception.InvalidParameterException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class OrderService {
    private final AtomicLong counter;
    private final List<Order> orders;
    private final Map<Order, OrderStatus> orderStates;

    public OrderService() {
        this.orders = new ArrayList<>();
        this.counter = new AtomicLong();
        this.orderStates = new HashMap<>();
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

        OrderSide side = quantity > 0 ? OrderSide.BUY : OrderSide.SELL;
        Order order = new Order(instrument, quantity, side);
        orders.add(order);
        orderStates.put(order, OrderStatus.CREATED);
        return order;
    }

    @SneakyThrows
    public void updateOrderSuccess(Order order, Long fillQty, Double fillPrice) {
//        OrderStatus state = orderStates.get(order);
//        if(state == null){
//            throw new InvalidOrderStateTransitionException();
//        }
//
//        if (state == OrderStatus.CREATED) {
//            order.setFilledQty(fillQty);
//            order.setAvgExecutedPrice(fillPrice);
//
//        } else if (state == OrderStatus.OPEN) {
//            Long currQty = order.getFilledQty();
//            Double currPrice = order.getAvgExecutedPrice();
//
//            double totalValue = currQty * currPrice + fillQty * fillPrice;
//            long totalFilledQty = currQty + fillQty;
//
//            order.setFilledQty(totalFilledQty);
//            order.setAvgExecutedPrice(totalValue / totalFilledQty);
//
//        } else {
//            log.warn("Order : " + order.getId() + " is not Open or new. Already actioned");
//            return;
//        }
//
//        order.setLastExchangeTime(Instant.now());
//        if (order.getTotalQty().equals(order.getFilledQty())) {
//            orderStates.put(order, OrderStatus.COMPLETED);
//        } else {
//            orderStates.put(order, OrderStatus.OPEN);
//        }
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
