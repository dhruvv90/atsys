package atsys.backtesting.engine;


import atsys.backtesting.components.ComponentsService;
import atsys.backtesting.engine.events.Event;
import atsys.backtesting.model.Backtest;
import atsys.backtesting.model.Order;

import java.util.HashMap;
import java.util.Map;

public class BacktestingContext {

    private final QueuePublisher queuePublisher;
    private final EventManager eventManager;
    private final Map<String, Long> positions;
    private final Map<Long, Order> orders;

    private final ComponentsService componentsService;


    public BacktestingContext(Backtest<?> backtest, QueuePublisher queuePublisher, EventManager eventManager){
        this.queuePublisher = queuePublisher;
        this.eventManager = eventManager;
        this.positions = new HashMap<>();
        this.orders = new HashMap<>();

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

    @Deprecated
    public void recordOrder(Order order){
        orders.putIfAbsent(order.getId(), order);
        positions.put(order.getSymbol(), order.getCurrQty());
    }

    public void end() {
        componentsService.onComplete();
    }

    public void publishEvent(Event event){
        queuePublisher.publishEvent(event);
    }

}
