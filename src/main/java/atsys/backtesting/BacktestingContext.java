package atsys.backtesting;


import atsys.backtesting.components.portfolio.PortfolioManager;
import atsys.backtesting.components.strategy.Strategy;
import atsys.backtesting.engine.EventPublisher;
import atsys.backtesting.engine.EventsRepository;
import atsys.backtesting.engine.events.Event;
import atsys.backtesting.engine.events.SignalEvent;
import atsys.backtesting.engine.events.TickEvent;
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

        registerStrategy();
        registerPortfolioManager();
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
    }

    public void destroy() {
        backtest.getStrategy().onComplete();
        backtest.getPortfolioManager().onComplete();
    }

    public void publishEvent(Event event){
        eventPublisher.publishEvent(event);
    }

}