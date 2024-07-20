package atsys.backtesting.engine;


import atsys.backtesting.engine.components.ComponentsService;
import atsys.backtesting.engine.components.TickData;
import atsys.backtesting.engine.components.order.Order;
import atsys.backtesting.engine.components.order.OrderService;
import atsys.backtesting.engine.components.order.OrderType;
import atsys.backtesting.engine.events.Event;
import atsys.backtesting.engine.events.FillEvent;
import atsys.backtesting.engine.events.OrderEvent;
import atsys.backtesting.engine.events.SignalEvent;
import atsys.backtesting.engine.components.signal.SignalType;
import atsys.backtesting.engine.exception.InitializationException;
import atsys.backtesting.engine.exception.InvalidOrderStateTransitionException;

import java.util.HashMap;
import java.util.Map;

public class BacktestingContext {

    private final QueuePublisher queuePublisher;
    private final EventManager eventManager;
    private final Map<String, Long> positions;
    private final OrderService orderService;
    private final ComponentsService componentsService;

    public BacktestingContext(Backtest<?> backtest, QueuePublisher queuePublisher, EventManager eventManager) throws InitializationException {
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
    private void publishEvent(Event event){
        queuePublisher.publishEvent(event);
    }

    public void publishOrder(String symbol, OrderType orderType, Long quantity){
        Order order = orderService.createOrder(symbol, orderType, quantity);
        OrderEvent event = new OrderEvent(order);
        publishEvent(event);
    }

    public void publishSignal(String symbol, SignalType signalType){
        SignalEvent event = new SignalEvent(symbol, signalType);
        publishEvent(event);
    }


    public void publishFill(Order order, Long filledQty) throws InvalidOrderStateTransitionException {
        orderService.onOrderPlaceSuccess(order, filledQty);
        FillEvent event = new FillEvent(order, filledQty);

        positions.put(order.getSymbol(), filledQty);
        publishEvent(event);
    }

    public TickData getLastTick(){
        return queuePublisher.getLastTick();
    }
}
