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
import lombok.Getter;

public class BacktestingContext {

    @Getter
    private final Backtest backtest;

    private final EventPublisher eventPublisher;
    private final EventsRepository eventsRepository;

    public BacktestingContext(Backtest backtest, EventPublisher eventPublisher, EventsRepository eventsRepository){
        this.backtest = backtest;
        this.eventPublisher = eventPublisher;
        this.eventsRepository = eventsRepository;

        registerExecutionManager();
        registerPortfolioManager();
        registerStrategy();
    }

    private void registerStrategy(){
        Strategy strategy = backtest.getStrategy();
        strategy.onInit(this);

        // Register Strategy as TickEventListener
        eventsRepository.register(TickEvent.class, new TickEventListener(backtest.getStrategy()));
    }

    private void registerPortfolioManager(){
        PortfolioManager portfolioManager = backtest.getPortfolioManager();
        portfolioManager.onInit(this);

        // Register PortfolioManager
        eventsRepository.register(SignalEvent.class, new SignalEventListener(backtest.getPortfolioManager()));
        eventsRepository.register(FillEvent.class, new FillEventListener(backtest.getPortfolioManager()));
    }

    private void registerExecutionManager(){
        ExecutionManager executionManager = backtest.getExecutionManager();
        executionManager.onInit(this);

        eventsRepository.register(OrderEvent.class, new OrderEventListener(backtest.getExecutionManager()));
    }

    public void destroy() {
        backtest.getStrategy().onComplete();
        backtest.getPortfolioManager().onComplete();
        backtest.getExecutionManager().onComplete();
    }

    public void publishEvent(Event event){
        eventPublisher.publishEvent(event);
    }

}
