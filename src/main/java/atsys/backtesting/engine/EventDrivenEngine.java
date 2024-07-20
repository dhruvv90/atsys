package atsys.backtesting.engine;

import atsys.backtesting.engine.events.Event;
import atsys.backtesting.engine.events.KillEvent;
import atsys.backtesting.engine.listeners.KillEventListener;
import atsys.backtesting.engine.exception.BaseException;
import atsys.backtesting.engine.model.Backtest;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

/**
 * A Re-usable Event Driven Engine used by the backtester
 */
@Slf4j
public class EventDrivenEngine {

    private final EventQueue<Event> eventQueue;
    private final EventManager eventManager;

    @Getter
    private final QueuePublisher publisher;

    private BacktestingContext currentContext;

    @Getter
    private BacktestingReport report;


    public EventDrivenEngine() {
        this.eventQueue = new EventQueueImpl();
        this.eventManager = new EventManager();
        this.publisher = new QueuePublisher(this.eventQueue);
    }

    public void initializeForBacktest(Backtest<?> backtest) {
        currentContext = new BacktestingContext(backtest, publisher, eventManager);
        this.report = new BacktestingReport();

        // Register KillEvent
        eventManager.register(KillEvent.class, new KillEventListener());
    }

    public void reset() {
        currentContext.end();
        currentContext = null;

        eventManager.unregisterAll();
        eventQueue.clear();
    }

    public boolean hasEvents(){
        return !eventQueue.isEmpty();
    }

    public void consumeEvent() throws BaseException{
        if(!hasEvents()){
            throw new BaseException();
        }
        Event event = eventQueue.poll();
        event.setConsumedAt(Instant.now());

        if(currentContext == null){
            log.warn("Engine is not attached to any Backtest. This event emission might not trigger any Lifecycle components");
        }
        eventManager.emit(event);
        report.recordEvent(event);
    }

    @SneakyThrows
    public void consumeAllEvents(){
        while (hasEvents()) {
            consumeEvent();
        }
    }

}
