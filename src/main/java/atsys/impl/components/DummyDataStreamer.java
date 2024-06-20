package atsys.impl.components;

import atsys.api.components.DataStreamer;
import atsys.impl.BacktestingInputs;
import atsys.impl.model.BaseTickData;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;


@Slf4j
public class DummyDataStreamer implements DataStreamer<BaseTickData> {

    private final BacktestingInputs backtestingInputs;
    private Iterator<BaseTickData> dataIter;

    public DummyDataStreamer(BacktestingInputs inputs) {
        backtestingInputs = inputs;
    }

    @Override
    public void onInit() {
        log.info("Ingesting dummy data..");
        List<BaseTickData> data = DummyDataHelper.generateData(backtestingInputs);
        dataIter = data.iterator();
    }

    @Override
    public BaseTickData readData() {
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
