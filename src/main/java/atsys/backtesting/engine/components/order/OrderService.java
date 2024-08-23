package atsys.backtesting.engine.components.order;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.backtesting.engine.components.trade.Trade;
import atsys.backtesting.engine.exception.InvalidParameterException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class OrderService {
    private final AtomicLong counter;
    private final Map<String, Order> ordersById;

    public OrderService() {
        this.ordersById = new HashMap<>();
        this.counter = new AtomicLong();
    }

    private String generateNewOrderId() {
        return String.valueOf(counter.incrementAndGet());
    }

    private void saveOrder(Order order){
        ordersById.put(order.getOrderId(), order);
    }

    @SneakyThrows
    public Order createOrder(Instrument instrument, Long quantity) {
        if(quantity == 0){
            throw new InvalidParameterException("Invalid quantity: 0");
        }
        OrderSide side = quantity > 0 ? OrderSide.BUY : OrderSide.SELL;
        Order order = new Order(generateNewOrderId(), instrument, quantity, side);
        saveOrder(order);

        return order;
    }

    @SneakyThrows
    private Order getOrderOrThrow(String orderId){
        Order order =  ordersById.get(orderId);
        if(order == null){
            throw new InvalidParameterException("Invalid orderId recieved : " + orderId);
        }
        return order;
    }

    public void processOrderFill(OrderFill fill){
        Order order = getOrderOrThrow(fill.getOrderId());

        order.setExchangeOrderId(fill.getExchangeOrderId());

        // change status
        if (Objects.requireNonNull(fill.getOrderFillStatus()) == OrderFillStatus.SUCCESS) {
            order.setOrderStatus(OrderStatus.COMPLETED);

        } else if (fill.getOrderFillStatus() == OrderFillStatus.REJECTED) {
            order.setOrderStatus(OrderStatus.REJECTED);
        }
    }

    public void stageOrder(OrderFill fill){
        Order order = getOrderOrThrow(fill.getOrderId());
        order.setOrderStatus(OrderStatus.STAGED);
    }

    public void addTradeToOrder(Trade trade) {
        String orderId = trade.getOrderId();
        Order order = getOrderOrThrow(orderId);
        order.addTrade(trade);
    }
}
