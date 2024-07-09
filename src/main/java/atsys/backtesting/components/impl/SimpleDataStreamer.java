package atsys.backtesting.components.impl;

import atsys.backtesting.components.DataStreamer;
import atsys.backtesting.exception.DataStreamerException;
import atsys.backtesting.model.Backtest;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Slf4j(topic = "Data")
public class SimpleDataStreamer implements DataStreamer<SimpleTickData> {

    private Iterator<SimpleTickData> dataIterator;

    /**
     * Initialize this dataStreamer for a particular backtest.
     * Should ideally preload all data required for a backtest
     */
    public void initializeForBacktest(Backtest<SimpleTickData> backtest) {
        log.info("{} initialized for backtest : {}", this.getClass().getSimpleName(), backtest.getName());
        List<SimpleTickData> data = generateDummyData(backtest);
        dataIterator = data.iterator();
    }

    private List<SimpleTickData> generateDummyData(Backtest<SimpleTickData> backtest){
        List<SimpleTickData> result = new ArrayList<>();
        for(int i = 0; i < 15; i++){
            SimpleTickData data = new SimpleTickData();
            data.setSymbol("Symbol("+ i + ")");
            data.setTickTimestamp(Instant.now());
            data.setLastTradedTime(Instant.now());
            data.setLastTradedPrice(Math.random() + Math.random() * 100);
            result.add(data);
        }
        return result;
    }


    public SimpleTickData readData() throws DataStreamerException {
        if(!this.hasNext()){
            throw new DataStreamerException();
        }
        return dataIterator.next();
    }

    public boolean hasNext() {
        return dataIterator.hasNext();
    }

    public void reset() {
        log.info("{} is reset for new backtest", this.getClass().getSimpleName());
    }
}
