package atsys.backtesting.components.strategy;

import atsys.backtesting.BacktestingContext;
import atsys.backtesting.engine.event.TickEvent;
import atsys.backtesting.model.TickData;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DummyStrategy implements Strategy{
    private int counter;

    @Override
    public void onInit(BacktestingContext context) {
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
//        log.info(sb);
        counter++;
    }
}
