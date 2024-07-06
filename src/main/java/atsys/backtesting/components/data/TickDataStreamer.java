package atsys.backtesting.components.data;

import atsys.backtesting.exception.DataStreamerException;
import atsys.backtesting.exception.InvalidDataAccessException;
import atsys.backtesting.model.Backtest;
import atsys.backtesting.model.TickData;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;


@Slf4j
public class TickDataStreamer {

    private Iterator<TickData> dataIterator;

    /**
     * Initialize this dataStreamer for a particular backtest.
     * Should ideally preload all data required for a backtest
     */
    public void initializeForBacktest(Backtest backtest) {
        log.info("{} initialized for backtest : {}", this.getClass().getSimpleName(), backtest.getName());
        List<TickData> data = DataHelper.generateDummyData(backtest);
        dataIterator = data.iterator();
    }


    public TickData readData() throws DataStreamerException {
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
