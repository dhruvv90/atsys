package atsys.backtesting.engine.components;

import atsys.backtesting.engine.BacktestingContext;
import atsys.backtesting.engine.EventManager;
import atsys.backtesting.engine.events.FillEvent;
import atsys.backtesting.engine.events.OrderEvent;
import atsys.backtesting.engine.events.SignalEvent;
import atsys.backtesting.engine.events.TickEvent;
import atsys.backtesting.engine.listeners.FillEventListener;
import atsys.backtesting.engine.listeners.OrderEventListener;
import atsys.backtesting.engine.listeners.SignalEventListener;
import atsys.backtesting.engine.listeners.TickEventListener;
import atsys.backtesting.engine.model.Backtest;
import lombok.SneakyThrows;


public class ComponentsService {
    private final EventManager eventManager;
    private final Backtest<?> backtest;
    private final BacktestingContext context;

    // Components
    private Strategy<?> strategy;
    private PortfolioManager portfolioManager;
    private ExecutionManager executionManager;


    public ComponentsService(BacktestingContext context, Backtest<?> backtest, EventManager eventManager){
        this.eventManager = eventManager;
        this.backtest = backtest;
        this.context = context;
    }

    public void registerComponents(){
        registerExecutionManager();
        registerPortfolioManager();
        registerStrategy();
    }

    public void onComplete(){
        strategy.onComplete();
        portfolioManager.onComplete();
        executionManager.onComplete();
    }

    @SneakyThrows
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void registerStrategy() {
        var clazz = backtest.getStrategyClazz();
        strategy = clazz.getDeclaredConstructor().newInstance();
        strategy.onInit(context);

        // Event Registration
        eventManager.register(TickEvent.class, new TickEventListener(strategy));
    }

    @SneakyThrows
    private void registerPortfolioManager(){
        var clazz = backtest.getPortfolioClazz();
        portfolioManager = clazz.getDeclaredConstructor().newInstance();
        portfolioManager.onInit(context);

        // Event Registration
        eventManager.register(SignalEvent.class, new SignalEventListener(portfolioManager));
        eventManager.register(FillEvent.class, new FillEventListener(portfolioManager));
    }

    @SneakyThrows
    private void registerExecutionManager(){
        var clazz = backtest.getExecutionMgrClazz();
        executionManager = clazz.getDeclaredConstructor().newInstance();
        executionManager.onInit(context);

        // Event Registration
        eventManager.register(OrderEvent.class, new OrderEventListener(executionManager));
    }
}
