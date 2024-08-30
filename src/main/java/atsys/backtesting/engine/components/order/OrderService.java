package atsys.backtesting.engine.components.order;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.backtesting.engine.components.id.IdManager;
import atsys.backtesting.engine.components.portfolio.Trade;
import atsys.backtesting.engine.exception.InvalidParameterException;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class OrderService {
    private final Map<String, Order> ordersById;

    @Getter
    private final IdManager idManager;

    public OrderService(IdManager idManager) {
        this.idManager = idManager;
        this.ordersById = new HashMap<>();
    }

    private void saveOrder(Order order){
        ordersById.put(order.getId(), order);
    }

    @SneakyThrows
    public Order createOrder(String signalId, Instrument instrument, long quantity) {
        if(quantity == 0){
            throw new InvalidParameterException("Invalid quantity: 0");
        }
        String newId = idManager.generateId(IdManager.ComponentType.ORDER);

        Order order = new Order(newId, signalId, instrument, quantity);
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
            order.setOrderStatus(OrderStatus.OPEN);

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
