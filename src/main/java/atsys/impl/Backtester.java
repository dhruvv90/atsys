package atsys.impl;

import atsys.api.TradingEngine;
import atsys.api.components.DataStreamer;
import atsys.api.components.Strategy;
import atsys.api.event.Event;
import atsys.api.event.EventEmitter;
import atsys.api.event.EventQueue;

public class Backtester implements TradingEngine {
    private EventQueue eventQueue;
    private DataStreamer dataStreamer;
    private Strategy strategy;
    private EventEmitter eventEmitter;


    @Override
    public void run() {
        onInit();
        while(dataStreamer.hasNext()){
            if(eventQueue.isEmpty()){
                dataStreamer.readData();
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
        dataStreamer.onInit();
        strategy.onInit();
    }

    @Override
    public void onComplete() {
        strategy.onComplete();
        dataStreamer.onComplete();
    }
}
