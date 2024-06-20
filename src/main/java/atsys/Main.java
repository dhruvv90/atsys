package atsys;


import atsys.impl.Backtester;
import atsys.impl.BacktestingInputs;
import atsys.impl.components.DummyDataStreamer;
import atsys.impl.components.DummyStrategy;
import atsys.impl.core.DefaultEventEmitter;
import atsys.impl.core.DefaultEventQueue;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        BacktestingInputs inputs = BacktestingInputs.builder()
                .initialCapital(BigDecimal.valueOf(100.4))
                .instruments(new ArrayList<>())
                .startDateTime(Instant.now())
                .endDateTime(Instant.now())
                .build();


        Backtester bt = new Backtester(
                new DummyDataStreamer(inputs),
                new DummyStrategy(),
                new DefaultEventQueue(),
                new DefaultEventEmitter());
        bt.run();
    }
}
