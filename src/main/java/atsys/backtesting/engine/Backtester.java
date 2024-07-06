package atsys.backtesting.engine;

import atsys.backtesting.model.Backtest;
import atsys.backtesting.engine.events.Event;
import atsys.backtesting.engine.events.TickEvent;
import atsys.backtesting.model.TickData;
import atsys.backtesting.components.data.TickDataStreamer;


public class Backtester {

    private final TickDataStreamer dataStreamer;
    private final EventDrivenEngine engine;

    public Backtester() {
        engine = new EventDrivenEngine();
        dataStreamer = new TickDataStreamer();
    }

    private void preBacktest(Backtest backtest){
        // Initialize dataStreamer
        dataStreamer.onInit(backtest);

        //Register backtest
        engine.registerBacktest(backtest);
    }

    private void postBacktest(Backtest backtest) {
        engine.unregisterBacktest();
        dataStreamer.onComplete();
    }

    /**
     * Run Backtest independently
     */
    public void run(Backtest backtest) {
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
