package atsys.backtesting.engine;


import atsys.backtesting.components.ExecutionManager;
import atsys.backtesting.components.PortfolioManager;
import atsys.backtesting.components.Strategy;
import atsys.backtesting.engine.events.*;
import atsys.backtesting.engine.listeners.FillEventListener;
import atsys.backtesting.engine.listeners.OrderEventListener;
import atsys.backtesting.engine.listeners.SignalEventListener;
import atsys.backtesting.engine.listeners.TickEventListener;
import atsys.backtesting.model.Backtest;

public class BacktestingContext {

    private final Backtest<?> backtest;
    private final QueuePublisher queuePublisher;
    private final EventManager eventManager;

    public BacktestingContext(Backtest<?> backtest, QueuePublisher queuePublisher, EventManager eventManager){
        this.backtest = backtest;
        this.queuePublisher = queuePublisher;
        this.eventManager = eventManager;

        registerExecutionManager();
        registerPortfolioManager();
        registerStrategy();
    }

    @SuppressWarnings("unchecked")
    private void registerStrategy(){
        Strategy<?> strategy = backtest.getStrategy();
        strategy.onInit(this);

        // Register Strategy as TickEventListener
        eventManager.register(TickEvent.class, new TickEventListener(backtest.getStrategy()));
    }

    private void registerPortfolioManager(){
        PortfolioManager portfolioManager = backtest.getPortfolioManager();
        portfolioManager.onInit(this);

        // Register PortfolioManager
        eventManager.register(SignalEvent.class, new SignalEventListener(backtest.getPortfolioManager()));
        eventManager.register(FillEvent.class, new FillEventListener(backtest.getPortfolioManager()));
    }

    private void registerExecutionManager(){
        ExecutionManager executionManager = backtest.getExecutionManager();
        executionManager.onInit(this);

        eventManager.register(OrderEvent.class, new OrderEventListener(backtest.getExecutionManager()));
    }

    public void end() {
        backtest.getStrategy().onComplete();
        backtest.getPortfolioManager().onComplete();
        backtest.getExecutionManager().onComplete();
    }

    public void publishEvent(Event event){
        queuePublisher.publishEvent(event);
    }

}
