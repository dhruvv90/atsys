package atsys.impl;

import atsys.api.LifecycleManager;
import atsys.api.components.Strategy;
import atsys.api.core.EventEmitter;
import atsys.api.core.EventQueue;
import atsys.api.core.event.Event;
import atsys.api.core.event.TickEvent;
import atsys.api.model.TickData;
import atsys.impl.components.TickDataStreamer;
import atsys.impl.core.DefaultEventEmitter;
import atsys.impl.core.DefaultEventQueue;
import atsys.impl.event.listener.TickEventListener;


public class Backtester {

    private final EventQueue<Event> eventQueue;
    private final TickDataStreamer dataStreamer;
    private final EventEmitter eventEmitter;

    public Backtester() {
        this.eventQueue = new DefaultEventQueue();
        this.dataStreamer = new TickDataStreamer();
        this.eventEmitter = new DefaultEventEmitter();
    }

    public void run(Backtest bt) {
        bt.onInit();

        dataStreamer.onInit();
        Strategy strategy = bt.getStrategy();
        TickEventListener tickListener = new TickEventListener(strategy);
        eventEmitter.register(TickEvent.class, tickListener);

        while (dataStreamer.hasNext()) {
            if (eventQueue.isEmpty()) {
                TickData data = dataStreamer.readData();
                eventQueue.offer(new TickEvent(data));
            }
            if (eventQueue.isEmpty()) {
                break;
            }
            Event ev = eventQueue.poll();
            eventEmitter.emit(ev);
        }

        eventEmitter.unregister(TickEvent.class, tickListener);

        this.eventQueue.clear();
        dataStreamer.onComplete();
        bt.onComplete();
    }
}
