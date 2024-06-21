package atsys;


import atsys.impl.Backtest;
import atsys.impl.Backtester;
import atsys.impl.components.DummyStrategy;

import java.time.Instant;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Backtest myBacktest = new Backtest(
                "myBacktest", "",
                new ArrayList<>(),
                1000,
                Instant.now(),
                Instant.now(),
                new DummyStrategy());

        Backtester btEngine = new Backtester();
        long startTime;
        long endTime;

        startTime = System.currentTimeMillis();
        btEngine.run(myBacktest);
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        startTime = System.currentTimeMillis();
        btEngine.run(myBacktest);
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        startTime = System.currentTimeMillis();
        btEngine.run(myBacktest);
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

    }
}
