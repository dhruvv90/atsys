package atsys.backtesting.engine;

import atsys.backtesting.BacktestingContext;
import atsys.backtesting.engine.events.Event;
import atsys.backtesting.engine.events.KillEvent;
import atsys.backtesting.engine.listeners.KillEventListener;
import atsys.backtesting.model.Backtest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * A Re-usable Event Driven Engine used by the backtester
 */
@Slf4j
public class EventDrivenEngine {

    private final EventQueue<Event> eventQueue;
    private final EventsRepository eventsRepository;

    @Getter
    private final EventConsumer consumer;

    @Getter
    private final EventPublisher publisher;

    private BacktestingContext currentContext;


    public EventDrivenEngine() {
        this.eventQueue = new EventQueueImpl();
        this.eventsRepository = new EventsRepository();

        this.consumer = new EventConsumer(this.eventQueue);
        this.publisher = new EventPublisher(this.eventQueue);
    }

    public void emitEvent(Event e) {
        if(currentContext == null){
            log.warn("Engine is not attached to any Backtest. This event emission will be futile!");
        }
        eventsRepository.emit(e);
    }

    public void reset() {
        currentContext.destroy();
        currentContext = null;

        eventsRepository.unregisterAll();
        eventQueue.clear();
    }

    public void initializeForBacktest(Backtest backtest) {
        currentContext = new BacktestingContext(backtest, publisher, eventsRepository);

        // Register KillEvent
        eventsRepository.register(KillEvent.class, new KillEventListener());
    }
}
