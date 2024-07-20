package atsys.backtesting.engine;

import atsys.backtesting.engine.components.ComponentsService;
import atsys.backtesting.engine.components.TickData;
import atsys.backtesting.engine.components.order.Order;
import atsys.backtesting.engine.components.order.OrderService;
import atsys.backtesting.engine.components.order.OrderType;
import atsys.backtesting.engine.components.position.Position;
import atsys.backtesting.engine.components.position.PositionService;
import atsys.backtesting.engine.components.signal.SignalType;
import atsys.backtesting.engine.events.Event;
import atsys.backtesting.engine.events.FillEvent;
import atsys.backtesting.engine.events.OrderEvent;
import atsys.backtesting.engine.events.SignalEvent;
import atsys.backtesting.engine.exception.InitializationException;

import java.util.Optional;


public class BacktestingContext {

    private final QueuePublisher queuePublisher;
    private final EventManager eventManager;
    private final OrderService orderService;
    private final PositionService positionService;
    private final ComponentsService componentsService;

    public BacktestingContext(Backtest<?> backtest, QueuePublisher queuePublisher, EventManager eventManager) throws InitializationException {
        this.queuePublisher = queuePublisher;
        this.eventManager = eventManager;
        this.positionService = new PositionService();
        this.orderService = new OrderService();

        this.componentsService = new ComponentsService(this, backtest, eventManager);
        this.componentsService.registerComponents();

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


    public void publishFill(Order order, Long filledQty, Double filledPrice) {
        orderService.onOrderPlaceSuccess(order, filledQty, filledPrice);

        FillEvent event = new FillEvent(order);
        positionService.addPosition(order.getSymbol(), filledQty);
        publishEvent(event);
    }

    public Optional<Position> getPosition(String symbol){
        return positionService.getPosition(symbol);
    }

    public TickData getLastTick(){
        return queuePublisher.getLastTick();
    }
}
