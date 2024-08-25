package atsys.backtesting.engine.components;

import atsys.backtesting.engine.*;
import atsys.backtesting.engine.components.order.OrderService;
import atsys.backtesting.engine.components.portfolio.PositionService;
import atsys.backtesting.engine.events.*;
import atsys.backtesting.engine.events.listeners.*;
import atsys.backtesting.engine.exception.InitializationException;
import lombok.Getter;


public class ComponentsManager {
    @Getter
    private final EventManager eventManager;

    @Getter
    private final EventQueue<Event> eventQueue;
    private final OrderService orderService;
    private final PositionService positionService;

    @Getter
    private DataStreamer<TickData> dataStreamer;

    @Getter
    private final QueuePublisher queuePublisher;

    // Components
    private final Backtest<TickData> backtest;
    private Strategy<TickData> strategy;
    private PortfolioManager portfolioManager;
    private ExecutionManager executionManager;


    @SuppressWarnings("unchecked")
    public ComponentsManager(Backtest<?> backtest) throws InitializationException {
        this.backtest = (Backtest<TickData>) backtest;

        // create event queue and its components
        this.eventQueue = new EventQueueImpl();
        this.queuePublisher = new QueuePublisher(this.eventQueue);
        this.eventManager = new EventManager();

        // Create Services
        this.orderService = new OrderService();
        this.positionService = new PositionService();

        // Create Backtesting context
        BacktestingContext context = new BacktestingContext(this.backtest, queuePublisher, orderService, positionService);

        // Register components
        registerComponents(context);
    }

    private void registerComponents(BacktestingContext context) throws InitializationException {
        registerDataStreamer();

        registerStrategy(context);
        registerPortfolioManager(context);
        registerExecutionManager(context);
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
    private void registerStrategy(BacktestingContext context) throws InitializationException {
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

    private void registerPortfolioManager(BacktestingContext context) throws InitializationException {
        try {
            var clazz = backtest.getPortfolioClazz();
            portfolioManager = clazz.getDeclaredConstructor().newInstance();

            portfolioManager.setOrderService(orderService);
            portfolioManager.setPositionService(positionService);
            portfolioManager.onInit(context);

            // Event Registration
            eventManager.register(SignalEvent.class, new SignalEventListener(portfolioManager));
            eventManager.register(FillEvent.class, new FillEventListener(portfolioManager));
            eventManager.register(TradeEvent.class, new TradeEventListener(portfolioManager));
        } catch (Exception e) {
            throw new InitializationException(e);
        }
    }

    private void registerExecutionManager(BacktestingContext context) throws InitializationException {
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
