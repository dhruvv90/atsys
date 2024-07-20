package atsys.backtesting.engine.order;

import atsys.backtesting.engine.exception.InvalidOrderStateTransition;
import lombok.SneakyThrows;

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

    @SneakyThrows
    public void onOrderPlaceSuccess(Order order, Long fillQty)  {
        OrderState state = order.getOrderState();
        if(state != OrderState.OPEN && state != OrderState.CREATED){
            throw new InvalidOrderStateTransition();
        }
        order.setCurrQty(fillQty);
        if(order.getInitialQty().equals(order.getCurrQty())){
            order.setOrderState(OrderState.COMPLETED);
        }
        else{
            order.setOrderState(OrderState.OPEN);
        }
    }

    @SneakyThrows
    public void onOrderCancel(Order order)  {
        if(order.getOrderState() != OrderState.OPEN){
            throw new InvalidOrderStateTransition();
        }
        order.setOrderState(OrderState.CANCELLED);
    }

    @SneakyThrows
    public void onOrderReject(Order order)  {
        if(order.getOrderState() != OrderState.OPEN){
            throw new InvalidOrderStateTransition();
        }
        order.setOrderState(OrderState.REJECTED);
    }
}
