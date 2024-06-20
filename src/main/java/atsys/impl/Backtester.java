package atsys.impl;

import atsys.api.TradingEngine;
import atsys.api.components.DataStreamer;
import atsys.api.components.Strategy;
import atsys.api.core.Event;
import atsys.api.core.EventEmitter;
import atsys.api.core.EventListener;
import atsys.api.core.EventQueue;
import atsys.api.model.TickData;
import atsys.impl.event.TickEvent;
import atsys.impl.event.listener.TickEventListener;


public class Backtester implements TradingEngine {
    private final EventQueue<Event> eventQueue;
    private final DataStreamer<TickData> dataStreamer;
    private final Strategy strategy;
    private final EventEmitter eventEmitter;


    public Backtester(DataStreamer dataStreamer,
                      Strategy strategy,
                      EventQueue<Event> eventQueue,
                      EventEmitter eventEmitter) {
        this.eventQueue = eventQueue;
        this.eventEmitter = eventEmitter;
        this.strategy = strategy;
        this.dataStreamer = dataStreamer;
    }

    @Override
    public void run() {
        onInit();
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
        onComplete();
    }

    @Override
    public void onInit() {
        EventListener<TickEvent> tickEventListener = new TickEventListener(this.strategy);
        eventEmitter.register(TickEvent.class, tickEventListener);

        dataStreamer.onInit();
        strategy.onInit();
    }

    @Override
    public void onComplete() {
        strategy.onComplete();
        dataStreamer.onComplete();
    }
}
