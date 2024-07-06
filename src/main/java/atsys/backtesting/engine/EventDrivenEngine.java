package atsys.backtesting.engine;

import atsys.backtesting.BacktestingContext;
import atsys.backtesting.engine.events.Event;
import atsys.backtesting.engine.events.KillEvent;
import atsys.backtesting.engine.listeners.KillEventListener;
import atsys.backtesting.model.Backtest;
import lombok.Getter;

/**
 * A Re-usable Event Driven Engine used by the backtester
 */
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
        eventsRepository.emit(e);
    }

    public void reset() {
        currentContext.destroy();

        eventsRepository.unregisterAll();
        eventQueue.clear();
    }

    public void initializeForBacktest(Backtest backtest) {
        currentContext = new BacktestingContext(backtest, publisher, eventsRepository);

        // Register KillEvent
        eventsRepository.register(KillEvent.class, new KillEventListener());
    }
}
