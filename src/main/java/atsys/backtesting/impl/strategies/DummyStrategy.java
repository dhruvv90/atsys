package atsys.backtesting.impl.strategies;

import atsys.backtesting.engine.components.Strategy;
import atsys.backtesting.impl.components.SimpleTickData;
import atsys.backtesting.engine.components.signal.SignalType;
import lombok.extern.slf4j.Slf4j;


@Slf4j(topic = "Strategy")
public class DummyStrategy extends Strategy<SimpleTickData> {
    private int counter;

    @Override
    public void handleTick(SimpleTickData tickData) {
        String sb = "counter: " + counter +
                ", Symbol: " + tickData.getInstrument() +
                ", timestamp: " + tickData.getTickTimestamp() +
                ", LTT: " + tickData.getLastTradedTime() +
                ", LTP: " + tickData.getLastTradedPrice();
        log.info(sb);
        counter++;

        if(counter % 5 == 0){
            context.publishSignal(tickData.getInstrument(), SignalType.BUY);
        }
    }
}
