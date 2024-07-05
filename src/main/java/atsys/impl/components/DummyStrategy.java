package atsys.impl.components;

import atsys.api.components.Strategy;
import atsys.api.core.event.TickEvent;
import atsys.api.model.TickData;
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
        counter = 0;
    }

    @Override
    public void handleTick(TickEvent event) {
        TickData tickData = event.getData();
        String sb = "counter: " + counter + ". Strategy calc. " +
                "Symbol: " + tickData.getSymbol() +
                ", timestamp: " + tickData.getTickTimestamp() +
                ", LTT: " + tickData.getLastTradedTime() +
                ", LTP: " + tickData.getLastTradedPrice();
        log.info(sb);
        counter++;
    }
}
