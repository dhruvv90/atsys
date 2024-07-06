package atsys.backtesting;

import atsys.backtesting.engine.*;
import atsys.backtesting.engine.event.Event;
import atsys.backtesting.engine.event.TickEvent;
import atsys.backtesting.model.TickData;
import atsys.backtesting.components.data.TickDataStreamer;


public class Backtester {

    private final EventDrivenEngine engine;


    public Backtester() {
        this.engine = new EventDrivenEngine();
    }

    public void run(Backtest bt) {
        TickDataStreamer dataStreamer = new TickDataStreamer();

        engine.registerStrategy(bt.getStrategy());

        EventConsumer eventConsumer = engine.getEventConsumer();
        EventPublisher eventPublisher = engine.getEventPublisher();

        bt.onInit();
        dataStreamer.onInit();

        // Either dataStreamer has some data , or eventConsumer has some events
        while (dataStreamer.hasNext() || eventConsumer.hasEvents()) {

            // Process all events in queue first..
            while(eventConsumer.hasEvents()){
                Event ev = eventConsumer.getEvent();
                engine.processEvent(ev);
            }

            // If DataStreamer still has data, Load it..
            if(dataStreamer.hasNext()){
                TickData data = dataStreamer.readData();
                eventPublisher.publishEvent(new TickEvent(data));
            }
        }

//        eventEmitter.unregister(TickEvent.class, tickListener);
        dataStreamer.onComplete();
        bt.onComplete();
    }
}
