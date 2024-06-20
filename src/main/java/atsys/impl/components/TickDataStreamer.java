package atsys.impl.components;

import atsys.api.components.DataStreamer;
import atsys.api.model.TickData;
import atsys.impl.BacktestingInputs;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;


@Slf4j
public class TickDataStreamer implements DataStreamer<TickData> {

    private final BacktestingInputs backtestingInputs;
    private Iterator<TickData> dataIter;

    public TickDataStreamer(BacktestingInputs inputs) {
        backtestingInputs = inputs;
    }

    @Override
    public void onInit() {
        log.info("Ingesting dummy data..");
        List<TickData> data = DataHelper.generateDummyData(backtestingInputs);
        dataIter = data.iterator();
    }

    @Override
    public TickData readData() {
        return dataIter.next();
    }

    @Override
    public boolean hasNext() {
        return dataIter.hasNext();
    }


    @Override
    public void onComplete() {
        log.info("Data Streaming completed");
    }
}
