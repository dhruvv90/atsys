package atsys.backtesting.engine.components;

import atsys.backtesting.engine.Backtest;
import atsys.backtesting.engine.BacktestingContext;
import atsys.backtesting.engine.EventManager;
import atsys.backtesting.engine.events.FillEvent;
import atsys.backtesting.engine.events.OrderEvent;
import atsys.backtesting.engine.events.SignalEvent;
import atsys.backtesting.engine.events.TickEvent;
import atsys.backtesting.engine.events.listeners.FillEventListener;
import atsys.backtesting.engine.events.listeners.OrderEventListener;
import atsys.backtesting.engine.events.listeners.SignalEventListener;
import atsys.backtesting.engine.events.listeners.TickEventListener;
import atsys.backtesting.engine.exception.InitializationException;


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

    public void registerComponents() throws InitializationException{
        registerExecutionManager();
        registerPortfolioManager();
        registerStrategy();
    }

    public void onComplete(){
        strategy.onComplete();
        portfolioManager.onComplete();
        executionManager.onComplete();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void registerStrategy() throws InitializationException {
        try{
            var clazz = backtest.getStrategyClazz();
            strategy = clazz.getDeclaredConstructor().newInstance();
            strategy.onInit(context);

            // Event Registration
            eventManager.register(TickEvent.class, new TickEventListener(strategy));
        }
        catch(Exception e){
            throw new InitializationException(e);
        }

    }

    private void registerPortfolioManager() throws InitializationException {
        try{
            var clazz = backtest.getPortfolioClazz();
            portfolioManager = clazz.getDeclaredConstructor().newInstance();
            portfolioManager.onInit(context);

            // Event Registration
            eventManager.register(SignalEvent.class, new SignalEventListener(portfolioManager));
            eventManager.register(FillEvent.class, new FillEventListener(portfolioManager));
        }
        catch (Exception e){
            throw new InitializationException(e);
        }
    }

    private void registerExecutionManager() throws InitializationException{
        try{
            var clazz = backtest.getExecutionMgrClazz();
            executionManager = clazz.getDeclaredConstructor().newInstance();
            executionManager.onInit(context);

            // Event Registration
            eventManager.register(OrderEvent.class, new OrderEventListener(executionManager));
        }
        catch (Exception e){
            throw new InitializationException(e);
        }
    }
}
