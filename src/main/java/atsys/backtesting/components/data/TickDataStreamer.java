package atsys.backtesting.components.data;

import atsys.backtesting.BacktestingContext;
import atsys.backtesting.components.LifecycleManager;
import atsys.backtesting.model.TickData;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;


@Slf4j
public class TickDataStreamer implements LifecycleManager {

    private Iterator<TickData> dataIterator;


    @Override
    public void onInit(BacktestingContext context) {
        log.info("Ingesting dummy data..");
        List<TickData> data = DataHelper.generateDummyData(context);
        dataIterator = data.iterator();
    }

    public TickData readData() {
        return dataIterator.next();
    }

    public boolean hasNext() {
        return dataIterator.hasNext();
    }

    @Override
    public void onComplete() {
        log.info("Data Streaming completed");
    }
}
