package atsys.backtesting.engine.components.order;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class OrderService {
    private final AtomicLong counter;
    private final List<Order> orders;

    public OrderService(){
        this.orders = new ArrayList<>();
        this.counter = new AtomicLong();
    }

    private long generateId() {
        return counter.incrementAndGet();
    }

    public Order createOrder(String symbol, OrderType orderType, Long quantity){
        long id = generateId();
        Order order = new Order(id, symbol, orderType, quantity);
        orders.add(order);
        return order;
    }

    public void onOrderPlaceSuccess(Order order, Long fillQty) {
        OrderState state = order.getOrderState();
        if(state != OrderState.OPEN && state != OrderState.CREATED){
            log.info("Order : " + order.getId() + " is not Open or new. Already actioned");
            return;
        }
        order.setCurrQty(fillQty);
        if(order.getInitialQty().equals(order.getCurrQty())){
            order.setOrderState(OrderState.COMPLETED);
        }
        else{
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
