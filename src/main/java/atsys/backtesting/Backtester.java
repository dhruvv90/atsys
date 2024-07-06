package atsys.backtesting;

import atsys.backtesting.engine.EventConsumer;
import atsys.backtesting.engine.EventDrivenEngine;
import atsys.backtesting.engine.EventPublisher;
import atsys.backtesting.exception.BaseException;
import atsys.backtesting.model.Backtest;
import atsys.backtesting.engine.events.Event;
import atsys.backtesting.engine.events.TickEvent;
import atsys.backtesting.model.TickData;
import atsys.backtesting.components.data.TickDataStreamer;


/**
 * A Re-usable Event Driven Backtester used to run backtests independently.
 * Engine, Streamer and other components must be reset after each backtest
 */
public class Backtester {

    private final TickDataStreamer dataStreamer;
    private final EventDrivenEngine engine;

    public Backtester() {
        engine = new EventDrivenEngine();
        dataStreamer = new TickDataStreamer();
    }

    /**
     * Prepare Backtester for a particular backtest.
     * All resource initialization must be here
     */
    private void preBacktest(Backtest backtest){
        dataStreamer.initializeForBacktest(backtest);
        engine.initializeForBacktest(backtest);
    }

    private void postBacktest(Backtest backtest) {
        engine.reset();
        dataStreamer.reset();
    }

    /**
     * Run Backtest independently
     */
    public void run(Backtest backtest) throws BaseException {
        preBacktest(backtest);

        // extract consumers and producers
        EventConsumer eventConsumer = engine.getConsumer();
        EventPublisher eventPublisher = engine.getPublisher();

        // Either dataStreamer has some data , or eventConsumer has some events
        while (dataStreamer.hasNext() || eventConsumer.hasEvents()) {

            // Process all events in queue first..
            while (eventConsumer.hasEvents()) {
                Event ev = eventConsumer.getEvent();
                engine.emitEvent(ev);
            }

            // If DataStreamer still has data, Load it..
            if (dataStreamer.hasNext()) {
                TickData data = dataStreamer.readData();
                eventPublisher.publishEvent(new TickEvent(data));
            }
        }

        postBacktest(backtest);
    }
}
