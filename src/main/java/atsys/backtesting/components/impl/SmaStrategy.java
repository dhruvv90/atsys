package atsys.backtesting.components.impl;

import atsys.backtesting.components.Strategy;
import atsys.backtesting.engine.events.SignalEvent;
import atsys.backtesting.model.SignalType;
import lombok.extern.slf4j.Slf4j;

import java.util.Deque;
import java.util.LinkedList;


@Slf4j(topic = "Strategy")
public class SmaStrategy extends Strategy<SimpleTickData> {
    private double movingAverage;
    private final Deque<Double> storage;
    private static final int period = 10;

    public SmaStrategy(){
        this.storage = new LinkedList<>();

    }

    @Override
    public void handleTick(SimpleTickData tickData) {
        double price = tickData.getLastTradedPrice();
        String symbol = tickData.getSymbol();

        if(storage.size() < period){
            storage.addLast(price);
            movingAverage = ((movingAverage * (storage.size() - 1)) + price) / storage.size();
            log.info("price : "+ price + ", no trade!");
            return;
        }
        else{
            movingAverage = ((movingAverage * storage.size()) - storage.pollFirst() + price) / (storage.size() + 1);
            storage.addLast(price);
        }

        Long currentPos = context.getPositionCount(symbol);

        SignalEvent event;
        if(currentPos <= 0 && price >= movingAverage){
            event = new SignalEvent(symbol, SignalType.BUY);
            context.publishEvent(event);
        }
        else if(currentPos >0 && price < movingAverage){
            event = new SignalEvent(symbol, SignalType.SELL);
            context.publishEvent(event);
        }
        log.info("price : " + price + ", ma: " + movingAverage);
    }
}
