package atsys.backtesting.engine;

import atsys.backtesting.engine.components.TickData;
import atsys.backtesting.engine.components.asset.Instrument;
import atsys.backtesting.engine.components.order.Order;
import atsys.backtesting.engine.components.order.OrderFill;
import atsys.backtesting.engine.components.order.OrderService;
import atsys.backtesting.engine.components.portfolio.Position;
import atsys.backtesting.engine.components.portfolio.PositionService;
import atsys.backtesting.engine.components.signal.Signal;
import atsys.backtesting.engine.components.signal.SignalType;
import atsys.backtesting.engine.components.portfolio.Trade;
import atsys.backtesting.engine.events.*;
import lombok.Getter;

import java.util.Optional;


public class BacktestingContext {

    private final QueuePublisher queuePublisher;
    private final OrderService orderService;
    private final PositionService positionService;

    @Getter
    private final Backtest<?> backtest;

    public BacktestingContext(
            Backtest<?> backtest,
            QueuePublisher queuePublisher,
            OrderService orderService,
            PositionService positionService
    )  {
        this.backtest = backtest;
        this.queuePublisher = queuePublisher;

        this.orderService = orderService;
        this.positionService = positionService;
    }


    // should be private unless we add supporting custom events..
    private void publishEvent(Event event) {
        queuePublisher.publishEvent(event);
    }

    public void publishOrder(Instrument instrument, Long quantity) {
        Order order = orderService.createOrder(instrument, quantity);
        OrderEvent event = new OrderEvent(order);
        publishEvent(event);
    }

    public void publishSignal(Instrument instrument, SignalType signalType) {
        Signal signal = new Signal(instrument, signalType);
        SignalEvent event = new SignalEvent(signal);
        publishEvent(event);
    }

    public void publishFill(OrderFill fill) {
        orderService.stageOrder(fill);
        FillEvent event = new FillEvent(fill);
        publishEvent(event);
    }

    public void publishTrade(Trade trade) {
        TradeEvent event = new TradeEvent(trade);
        publishEvent(event);
    }

    public Optional<Position> getPosition(Instrument instrument) {
        return positionService.getPosition(instrument);
    }

    public TickData getLastTick() {
        return queuePublisher.getLastTick();
    }

}
