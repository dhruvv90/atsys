package atsys.impl.components;

import atsys.api.components.Strategy;
import atsys.api.model.TickData;
import atsys.impl.model.BaseTickData;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DummyStrategy implements Strategy{
    private int counter;

    @Override
    public void onInit() {
        log.info("Initiating Strategy..");
    }

    @Override
    public void onComplete() {
        log.info("Ending Strategy..");
    }


    @Override
    public <T extends TickData> void handleData(T data) {
        BaseTickData tickData = (BaseTickData) data;
        String sb = "counter: " + counter + ". Strategy calc. " +
                "OI: " + tickData.getOi() +
                ", timestamp: " + tickData.getTickTimestamp() +
                ", LTT: " + tickData.getLastTradedTime() +
                ", LTP: " + tickData.getLastTradedPrice();
        log.info(sb);
        counter++;
    }
}
