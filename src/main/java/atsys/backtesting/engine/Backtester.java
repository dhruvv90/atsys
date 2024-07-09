package atsys.backtesting.engine;

import atsys.backtesting.exception.BaseException;
import atsys.backtesting.model.Backtest;
import atsys.backtesting.engine.events.TickEvent;
import atsys.backtesting.components.impl.SimpleTickData;
import atsys.backtesting.components.data.TickDataStreamer;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


/**
 * A Re-usable Event Driven Backtester used to run backtests independently.
 * Engine, Streamer and other components must be reset after each backtest
 */
@Slf4j(topic = "Backtester")
public class Backtester {

    private final TickDataStreamer dataStreamer;
    private final EventDrivenEngine engine;
    private final Map<Backtest, BacktestingReport> backtestingReports;

    public Backtester() {
        engine = new EventDrivenEngine();
        dataStreamer = new TickDataStreamer();
        this.backtestingReports = new HashMap<>();
    }

    /**
     * Prepare Backtester for a particular backtest.
     * All resource initialization must be here
     */
    private void preBacktest(Backtest backtest){
        log.info("Initiating Backtest : {}", backtest.getName());
        dataStreamer.initializeForBacktest(backtest);
        engine.initializeForBacktest(backtest);
    }

    private void postBacktest(Backtest backtest) {
        log.info("Ending Backtest : {}", backtest.getName());
        backtestingReports.put(backtest, engine.getReport());
        engine.reset();
        dataStreamer.reset();
    }

    /**
     * Run Backtest independently
     */
    public void run(Backtest backtest) throws BaseException {
        preBacktest(backtest);

        // producer
        QueuePublisher queuePublisher = engine.getPublisher();

        // Either dataStreamer has some data , or engine has some events
        while (dataStreamer.hasNext() || engine.hasEvents()) {

            // Process all events in queue first..
            engine.consumeAllEvents();

            // If DataStreamer still has data, Load it..
            if (dataStreamer.hasNext()) {
                SimpleTickData data = dataStreamer.readData();
                queuePublisher.publishEvent(new TickEvent(data));
            }
        }

        postBacktest(backtest);
    }
}
