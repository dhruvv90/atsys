package atsys.impl.components;

import atsys.api.components.Strategy;
import atsys.api.core.EventListener;
import atsys.impl.event.TickEvent;
import atsys.impl.model.BaseTickData;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DummyStrategy implements Strategy, EventListener<TickEvent> {
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
    public void onEvent(TickEvent event) {
        BaseTickData tickData = event.getData();
        String sb = "counter: " +  counter + ". Strategy calc. " +
                "OI: " + tickData.getOi() +
                ", timestamp: " + tickData.getTickTimestamp() +
                ", LTT: " + tickData.getLastTradedTime() +
                ", LTP: " + tickData.getLastTradedPrice();
        log.info(sb);
        counter++;
    }
}
