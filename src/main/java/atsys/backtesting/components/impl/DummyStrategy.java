package atsys.backtesting.components.impl;

import atsys.backtesting.engine.BacktestingContext;
import atsys.backtesting.components.Strategy;
import atsys.backtesting.engine.events.SignalEvent;
import atsys.backtesting.model.OrderType;
import lombok.extern.slf4j.Slf4j;


@Slf4j(topic = "Strategy")
public class DummyStrategy implements Strategy<SimpleTickData> {
    private int counter;
    private BacktestingContext context;

    @Override
    public void onInit(BacktestingContext context) {
        this.context = context;
        log.info("Initiating Strategy..");
    }

    @Override
    public void onComplete() {
        log.info("Ending Strategy..");
        counter = 0;
    }

    @Override
    public void handleTick(SimpleTickData tickData) {
        String sb = "counter: " + counter +
                ", Symbol: " + tickData.getSymbol() +
                ", timestamp: " + tickData.getTickTimestamp() +
                ", LTT: " + tickData.getLastTradedTime() +
                ", LTP: " + tickData.getLastTradedPrice();
        log.info(sb);
        counter++;

        if(counter % 5 == 0){
            context.publishEvent(new SignalEvent("test", OrderType.BUY));
        }
    }
}
