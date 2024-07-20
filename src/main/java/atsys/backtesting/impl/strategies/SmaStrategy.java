package atsys.backtesting.impl.strategies;

import atsys.backtesting.engine.components.Strategy;
import atsys.backtesting.impl.components.SimpleTickData;
import atsys.backtesting.engine.components.signal.SignalType;
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

        if(currentPos <= 0 && price >= movingAverage){
            context.publishSignal(symbol, SignalType.BUY);
        }
        else if(currentPos >0 && price < movingAverage){
            context.publishSignal(symbol, SignalType.SELL);
        }
        log.info("price : " + price + ", ma: " + movingAverage);
    }
}
