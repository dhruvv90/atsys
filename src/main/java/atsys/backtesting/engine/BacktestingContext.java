package atsys.backtesting.engine;


import atsys.backtesting.components.ComponentsService;
import atsys.backtesting.components.TickData;
import atsys.backtesting.components.order.Order;
import atsys.backtesting.components.order.OrderService;
import atsys.backtesting.components.order.OrderType;
import atsys.backtesting.engine.events.Event;
import atsys.backtesting.engine.events.FillEvent;
import atsys.backtesting.engine.events.OrderEvent;
import atsys.backtesting.engine.events.TickEvent;
import atsys.backtesting.model.Backtest;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class BacktestingContext {

    private final QueuePublisher queuePublisher;
    private final EventManager eventManager;
    private final Map<String, Long> positions;
    private final OrderService orderService;
    private final ComponentsService componentsService;

    @Getter
    private TickData lastTickData;


    public BacktestingContext(Backtest<?> backtest, QueuePublisher queuePublisher, EventManager eventManager){
        this.queuePublisher = queuePublisher;
        this.eventManager = eventManager;
        this.positions = new HashMap<>();
        this.orderService = new OrderService();

        this.componentsService = new ComponentsService(this, backtest, eventManager);
        this.componentsService.registerComponents();
    }

    @Deprecated
    public int getAllPositionsCount(){
        return positions.size();
    }

    @Deprecated
    public Long getPositionCount(String symbol){
        if(!positions.containsKey(symbol)){
            return 0L;
        }
        return positions.get(symbol);
    }

    public void end() {
        componentsService.onComplete();
    }

    // should be private unless we add supporting custom events..
    public void publishEvent(Event event){
        if(event.getClass().isAssignableFrom(TickEvent.class)){
            TickEvent<?> tickEvent = (TickEvent<?>) event;
            lastTickData = tickEvent.getData();
        }

        queuePublisher.publishEvent(event);
    }

    public void publishOrder(String symbol, OrderType orderType, Long quantity){
        Order order = orderService.createOrder(symbol, orderType, quantity);
        OrderEvent event = new OrderEvent(order);
        publishEvent(event);
    }

    public void publishFill(Order order, Long filledQty){
        orderService.onOrderPlaceSuccess(order, filledQty);
        FillEvent event = new FillEvent(order, filledQty);

        positions.put(order.getSymbol(), filledQty);
        publishEvent(event);
    }

}
