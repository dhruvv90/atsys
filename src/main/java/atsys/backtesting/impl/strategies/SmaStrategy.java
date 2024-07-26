package atsys.backtesting.impl.strategies;

import atsys.backtesting.engine.components.Strategy;
import atsys.backtesting.engine.components.asset.Instrument;
import atsys.backtesting.engine.components.position.Position;
import atsys.backtesting.impl.components.SimpleTickData;
import atsys.backtesting.engine.components.signal.SignalType;
import lombok.extern.slf4j.Slf4j;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;


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
        Instrument instrument = tickData.getInstrument();

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

        Optional<Position> currentPos = context.getPosition(instrument);
        long currQty = currentPos.map(Position::getQuantity).orElse(0L);


        if(currQty <= 0 && price >= movingAverage){
            context.publishSignal(instrument, SignalType.BUY);
        }
        else if(currQty >0 && price < movingAverage){
            context.publishSignal(instrument, SignalType.SELL);
        }
        log.info("price : " + price + ", ma: " + movingAverage);
    }
}
