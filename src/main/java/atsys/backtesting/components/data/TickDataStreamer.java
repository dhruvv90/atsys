package atsys.backtesting.components.data;

import atsys.backtesting.Backtest;
import atsys.backtesting.model.TickData;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;


@Slf4j
public class TickDataStreamer implements DataStreamer<TickData> {

    private Iterator<TickData> dataIterator;


    public void onInit(Backtest backtest) {
        log.info("Ingesting dummy data..");
        List<TickData> data = DataHelper.generateDummyData(backtest);
        dataIterator = data.iterator();
    }

    @Override
    public TickData readData() {
        return dataIterator.next();
    }

    @Override
    public boolean hasNext() {
        return dataIterator.hasNext();
    }

    public void onComplete() {
        log.info("Data Streaming completed");
    }
}
