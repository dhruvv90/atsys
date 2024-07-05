package atsys.backtesting.components;

import atsys.backtesting.model.TickData;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;


@Slf4j
public class TickDataStreamer implements DataStreamer<TickData> {

    private Iterator<TickData> dataIterator;

    @Override
    public void onInit() {
        log.info("Ingesting dummy data..");
        List<TickData> data = DataHelper.generateDummyData();
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


    @Override
    public void onComplete() {
        log.info("Data Streaming completed");
    }
}
