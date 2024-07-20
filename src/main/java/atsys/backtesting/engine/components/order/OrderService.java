package atsys.backtesting.engine.components.order;

import atsys.backtesting.engine.exception.InvalidOrderStateTransitionException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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

    public void onOrderPlaceSuccess(Order order, Long fillQty) throws InvalidOrderStateTransitionException {
        OrderState state = order.getOrderState();
        if(state != OrderState.OPEN && state != OrderState.CREATED){
            throw new InvalidOrderStateTransitionException();
        }
        order.setCurrQty(fillQty);
        if(order.getInitialQty().equals(order.getCurrQty())){
            order.setOrderState(OrderState.COMPLETED);
        }
        else{
            order.setOrderState(OrderState.OPEN);
        }
    }

    public void onOrderCancel(Order order) throws InvalidOrderStateTransitionException {
        if(order.getOrderState() != OrderState.OPEN){
            throw new InvalidOrderStateTransitionException();
        }
        order.setOrderState(OrderState.CANCELLED);
    }

    public void onOrderReject(Order order) throws InvalidOrderStateTransitionException {
        if(order.getOrderState() != OrderState.OPEN){
            throw new InvalidOrderStateTransitionException();
        }
        order.setOrderState(OrderState.REJECTED);
    }
}
