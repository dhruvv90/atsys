package atsys.backtesting.engine.components;

import atsys.backtesting.engine.*;
import atsys.backtesting.engine.components.order.OrderService;
import atsys.backtesting.engine.components.position.PositionService;
import atsys.backtesting.engine.events.*;
import atsys.backtesting.engine.events.listeners.FillEventListener;
import atsys.backtesting.engine.events.listeners.OrderEventListener;
import atsys.backtesting.engine.events.listeners.SignalEventListener;
import atsys.backtesting.engine.events.listeners.TickEventListener;
import atsys.backtesting.engine.exception.InitializationException;
import lombok.Getter;


public class ComponentsInitializer {
    @Getter
    private final EventManager eventManager;

    @Getter
    private final EventQueue<Event> eventQueue;
    private final OrderService orderService;
    private final PositionService positionService;

    @Getter
    private DataStreamer<TickData> dataStreamer;

    private final BacktestingContext context;

    @Getter
    private final QueuePublisher queuePublisher;

    // Components
    private final Backtest<TickData> backtest;
    private Strategy<TickData> strategy;
    private PortfolioManager portfolioManager;
    private ExecutionManager executionManager;


    @SuppressWarnings("unchecked")
    public ComponentsInitializer(Backtest<?> backtest) throws InitializationException {
        this.backtest = (Backtest<TickData>) backtest;

        // create event queue and its components
        this.eventQueue = new EventQueueImpl();
        this.queuePublisher = new QueuePublisher(this.eventQueue);
        this.eventManager = new EventManager();

        // Create Services
        this.orderService = new OrderService();
        this.positionService = new PositionService();

        // Create Backtesting context
        this.context = new BacktestingContext(this.backtest, queuePublisher, orderService, positionService);

        // Register components
        registerComponents();
    }

    private void registerComponents() throws InitializationException {
        registerDataStreamer();
        registerStrategy();
        registerPortfolioManager();
        registerExecutionManager();
    }

    public void onBacktestEnd() {
        eventManager.unregisterAll();

        strategy.onComplete();
        portfolioManager.onComplete();
        executionManager.onComplete();
    }

    private void registerDataStreamer() throws InitializationException {
        try {
            dataStreamer = backtest.getDataStreamer();
            dataStreamer.initializeForBacktest(backtest);

        } catch (Exception e) {
            throw new InitializationException(e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void registerStrategy() throws InitializationException {
        try {
            var clazz = backtest.getStrategyClazz();
            strategy = clazz.getDeclaredConstructor().newInstance();
            strategy.onInit(context);

            // Event Registration
            eventManager.register(TickEvent.class, new TickEventListener(strategy));
        } catch (Exception e) {
            throw new InitializationException(e);
        }

    }

    private void registerPortfolioManager() throws InitializationException {
        try {
            var clazz = backtest.getPortfolioClazz();
            portfolioManager = clazz.getDeclaredConstructor().newInstance();
            portfolioManager.onInit(context);

            // Event Registration
            eventManager.register(SignalEvent.class, new SignalEventListener(portfolioManager));
            eventManager.register(FillEvent.class, new FillEventListener(portfolioManager));
        } catch (Exception e) {
            throw new InitializationException(e);
        }
    }

    private void registerExecutionManager() throws InitializationException {
        try {
            var clazz = backtest.getExecutionMgrClazz();
            executionManager = clazz.getDeclaredConstructor().newInstance();
            executionManager.onInit(context);

            // Event Registration
            eventManager.register(OrderEvent.class, new OrderEventListener(executionManager));
        } catch (Exception e) {
            throw new InitializationException(e);
        }
    }
}
