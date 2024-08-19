package atsys.backtesting.engine;

import atsys.backtesting.engine.components.ComponentsInitializer;
import atsys.backtesting.engine.components.DataStreamer;
import atsys.backtesting.engine.components.TickData;
import atsys.backtesting.engine.events.Event;
import atsys.backtesting.engine.events.KillEvent;
import atsys.backtesting.engine.events.TickEvent;
import atsys.backtesting.engine.events.listeners.KillEventListener;
import atsys.backtesting.engine.exception.BaseException;
import atsys.backtesting.engine.exception.InitializationException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

/**
 * A Re-usable Event Driven Engine used by the backtester
 */
@Slf4j
public class EventDrivenEngine {

    @Getter
    private final BacktestingReport report;
    private final ComponentsInitializer componentsInitializer;
    private final Backtest<?> backtest;


    public EventDrivenEngine(Backtest<?> backtest) throws InitializationException {
        this.backtest = backtest;
        this.componentsInitializer = new ComponentsInitializer(backtest);
        this.report = new BacktestingReport();
    }


    public void run() throws BaseException {
        log.info("Initiating Backtest : {}", backtest.getName());

        DataStreamer<?> dataStreamer = componentsInitializer.getDataStreamer();
        EventManager eventManager = componentsInitializer.getEventManager();
        EventQueue<?> eventQueue = componentsInitializer.getEventQueue();
        QueuePublisher queuePublisher = componentsInitializer.getQueuePublisher();

        // Register KillEvent
        eventManager.register(KillEvent.class, new KillEventListener());

        // Either dataStreamer has some data , or engine has some events
        while (dataStreamer.hasNext() || !eventQueue.isEmpty()) {

            // Process all events in queue first..
            while (!eventQueue.isEmpty()) {
                Event event = eventQueue.poll();
                event.setConsumedAt(Instant.now());

                eventManager.emit(event);
                report.recordEvent(event);
            }

            // If DataStreamer still has data, Load it
            if (dataStreamer.hasNext()) {
                TickData data = dataStreamer.readData();
                queuePublisher.publishEvent(new TickEvent<>(data));
            }
        }

        componentsInitializer.onBacktestEnd();
        log.info("Ending Backtest : {}", backtest.getName());
    }
}
