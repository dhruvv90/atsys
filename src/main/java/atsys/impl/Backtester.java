package atsys.impl;

import atsys.api.TradingEngine;
import atsys.api.components.DataStreamer;
import atsys.api.event.Event;
import atsys.api.event.EventEmitter;
import atsys.api.event.EventQueue;
import atsys.impl.components.DummyDataStreamer;
import atsys.impl.components.DummyStrategy;
import atsys.impl.event.DefaultEventEmitter;
import atsys.impl.event.DefaultEventQueue;
import atsys.impl.event.TickEvent;
import atsys.impl.model.BaseTickData;

public class Backtester implements TradingEngine {
    private final  EventQueue<Event> eventQueue;
    private final DataStreamer<BaseTickData> dataStreamer;
    private final DummyStrategy strategy;
    private final EventEmitter eventEmitter;


    public Backtester(BacktestingInputs inputs){
        this.eventQueue = new DefaultEventQueue();
        this.eventEmitter = new DefaultEventEmitter();
        this.strategy = new DummyStrategy();
        this.dataStreamer = new DummyDataStreamer(inputs);
    }

    @Override
    public void run() {
        onInit();
        while(dataStreamer.hasNext()){
            if(eventQueue.isEmpty()){
                BaseTickData data = dataStreamer.readData();
                TickEvent event  = new TickEvent(data);
                eventQueue.offer(event);
            }
            if(eventQueue.isEmpty()){
                break;
            }
            Event ev = eventQueue.poll();
            eventEmitter.emit(ev);
        }
        onComplete();
    }

    @Override
    public void onInit() {
        eventEmitter.register(TickEvent.class, strategy);

        dataStreamer.onInit();
        strategy.onInit();
    }

    @Override
    public void onComplete() {
        strategy.onComplete();
        dataStreamer.onComplete();
    }
}
