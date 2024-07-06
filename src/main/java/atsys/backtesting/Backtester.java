package atsys.backtesting;

import atsys.backtesting.components.strategy.Strategy;
import atsys.backtesting.engine.*;
import atsys.backtesting.engine.event.Event;
import atsys.backtesting.engine.event.TickEvent;
import atsys.backtesting.engine.listeners.TickEventListener;
import atsys.backtesting.model.TickData;
import atsys.backtesting.components.data.TickDataStreamer;


public class Backtester {

    private final EventQueue<Event> eventQueue;
    private final EventConsumer eventConsumer;
    private final EventPublisher eventPublisher;
    private final EventEmitter eventEmitter;
    private final TickDataStreamer dataStreamer;

    public Backtester() {
        eventQueue = new EventQueueImpl();
        eventEmitter = new EventEmitter();
        dataStreamer = new TickDataStreamer();
        eventConsumer = new EventConsumer(eventQueue);
        eventPublisher = new EventPublisher(eventQueue);
    }

    private void postBacktest(Backtest bt) {
        eventQueue.clear();
        eventEmitter.unregisterAll();

        dataStreamer.onComplete();
        bt.onComplete();
    }

    private void preBacktest(Backtest bt){
        // Initialize backtest..
        bt.onInit();

        // Initialize dataStreamer
        dataStreamer.onInit(bt);


        // Register Strategy..
        Strategy strategy = bt.getStrategy();
        eventEmitter.register(TickEvent.class, new TickEventListener(strategy));
    }

    /**
     * Run Backtest independently
     */
    public void run(Backtest bt) {
        preBacktest(bt);

        // Either dataStreamer has some data , or eventConsumer has some events
        while (dataStreamer.hasNext() || eventConsumer.hasEvents()) {

            // Process all events in queue first..
            while (eventConsumer.hasEvents()) {
                Event ev = eventConsumer.getEvent();
                eventEmitter.emit(ev);
            }

            // If DataStreamer still has data, Load it..
            if (dataStreamer.hasNext()) {
                TickData data = dataStreamer.readData();
                eventPublisher.publishEvent(new TickEvent(data));
            }
        }

        postBacktest(bt);
    }
}
