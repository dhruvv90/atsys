package atsys.backtesting.impl.components;

import atsys.backtesting.engine.components.DataStreamer;
import atsys.backtesting.engine.components.asset.Equity;
import atsys.backtesting.engine.exception.DataStreamingException;
import atsys.backtesting.engine.Backtest;
import atsys.utils.Decimal;
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
            data.setInstrument(new Equity("testSymbol"));
            data.setTickTimestamp(Instant.now());
            data.setLastTradedTime(Instant.now());
            data.setLastTradedPrice(Decimal.valueOf(Math.random() + Math.random() * 100));
            result.add(data);
        }
        return result;
    }


    public SimpleTickData readData() throws DataStreamingException {
        if(!this.hasNext()){
            throw new DataStreamingException();
        }
        return dataIterator.next();
    }

    public boolean hasNext() {
        return dataIterator.hasNext();
    }
}
