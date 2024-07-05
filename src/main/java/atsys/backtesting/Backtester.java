package atsys.backtesting;

import atsys.backtesting.engine.EventQueue;
import atsys.backtesting.engine.event.Event;
import atsys.backtesting.engine.event.TickEvent;
import atsys.backtesting.model.TickData;
import atsys.backtesting.components.data.TickDataStreamer;
import atsys.backtesting.engine.EventEmitter;
import atsys.backtesting.engine.DefaultEventQueue;
import atsys.backtesting.engine.listeners.TickEventListener;


public class Backtester {

    private final EventQueue<Event> eventQueue;
    private final TickDataStreamer dataStreamer;
    private final EventEmitter eventEmitter;

    public Backtester() {
        this.eventQueue = new DefaultEventQueue();
        this.dataStreamer = new TickDataStreamer();
        this.eventEmitter = new EventEmitter();
    }

    public void run(Backtest bt) {
        bt.onInit();
        dataStreamer.onInit();
        eventQueue.clear();

        TickEventListener tickListener = new TickEventListener(bt.getStrategy());
        eventEmitter.register(TickEvent.class, tickListener);

        // Either dataStreamer has some data , or eventQueue has some events
        while (dataStreamer.hasNext() || !eventQueue.isEmpty()) {

            // Process all events in queue first..
            while(!eventQueue.isEmpty()){
                Event ev = eventQueue.poll();
                eventEmitter.emit(ev);
            }

            // If DataStreamer still has data, Load it..
            if(dataStreamer.hasNext()){
                TickData data = dataStreamer.readData();
                eventQueue.offer(new TickEvent(data));
            }
        }

        eventEmitter.unregister(TickEvent.class, tickListener);
        this.eventQueue.clear();
        dataStreamer.onComplete();
        bt.onComplete();
    }
}
