package atsys.backtesting;

import atsys.backtesting.engine.*;
import atsys.backtesting.engine.event.Event;
import atsys.backtesting.engine.event.TickEvent;
import atsys.backtesting.engine.listeners.TickEventListener;
import atsys.backtesting.model.TickData;
import atsys.backtesting.components.data.TickDataStreamer;
import lombok.Getter;


public class Backtester {

    private final EventQueue<Event> eventQueue;
    private final EventConsumer eventConsumer;
    private final EventPublisher eventPublisher;
    private final EventEmitter eventEmitter;


    public Backtester() {
        this.eventQueue = new EventQueueImpl();
        this.eventEmitter = new EventEmitter();

        this.eventConsumer = new EventConsumer(eventQueue);
        this.eventPublisher = new EventPublisher(eventQueue);
    }

    private void reset() {
        this.eventQueue.clear();
    }

    public void run(Backtest bt) {
        TickDataStreamer dataStreamer = new TickDataStreamer();

        TickEventListener tickListener = new TickEventListener(bt.getStrategy());
        eventEmitter.register(TickEvent.class, tickListener);

        bt.onInit();
        dataStreamer.onInit();

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

        eventEmitter.unregister(TickEvent.class, tickListener);
        dataStreamer.onComplete();
        bt.onComplete();

        this.reset();
    }
}
