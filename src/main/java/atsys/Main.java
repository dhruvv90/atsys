package atsys;


import atsys.impl.Backtester;
import atsys.impl.BacktestingInputs;

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

        Backtester bt = new Backtester(inputs);
        bt.run();
    }
}
