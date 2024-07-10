package atsys.backtesting.engine;

import atsys.backtesting.components.TickData;
import atsys.backtesting.components.DataStreamer;
import atsys.backtesting.engine.events.TickEvent;
import atsys.backtesting.exception.BaseException;
import atsys.backtesting.exception.DataStreamerException;
import atsys.backtesting.model.Backtest;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


/**
 * A Re-usable Event Driven Backtester used to run backtests independently.
 * Engine, Streamer and other components must be reset after each backtest
 */
@Slf4j(topic = "Setup")
public class Backtester {

    private final EventDrivenEngine engine;
    private final Map<Backtest<?>, BacktestingReport> backtestingReports;

    public Backtester() {
        engine = new EventDrivenEngine();
        this.backtestingReports = new HashMap<>();
    }

    /**
     * Prepare Backtester for a particular backtest.
     * All resource initialization must be here
     */
    private<T extends TickData> void preBacktest(Backtest<T> backtest, DataStreamer<T> dataStreamer) throws DataStreamerException {
        log.info("Initiating Backtest : {}", backtest.getName());
        dataStreamer.initializeForBacktest(backtest);
        engine.initializeForBacktest(backtest);
    }

    private <T extends TickData> void postBacktest(Backtest<T> backtest, DataStreamer<T> dataStreamer) {
        log.info("Ending Backtest : {}", backtest.getName());
        backtestingReports.put(backtest, engine.getReport());
        engine.reset();
        dataStreamer.reset();
    }

    /**
     * Run Backtest independently
     */
    public <T extends TickData> void run(Backtest<T> backtest, DataStreamer<T> dataStreamer) throws BaseException {
        preBacktest(backtest, dataStreamer);

        // producer
        QueuePublisher queuePublisher = engine.getPublisher();

        // Either dataStreamer has some data , or engine has some events
        while (dataStreamer.hasNext() || engine.hasEvents()) {

            // Process all events in queue first..
            engine.consumeAllEvents();

            // If DataStreamer still has data, Load it..
            if (dataStreamer.hasNext()) {
                T data = dataStreamer.readData();
                queuePublisher.publishEvent(new TickEvent<>(data));
            }
        }

        postBacktest(backtest, dataStreamer);
    }
}
