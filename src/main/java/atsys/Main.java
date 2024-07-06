package atsys;


import atsys.backtesting.Backtest;
import atsys.backtesting.Backtester;
import atsys.backtesting.components.strategy.DummyStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;


public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        tryBacktester();
    }

    private static void tryBacktester(){
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

        for(int i = 0; i < 3; i++){
            startTime = System.currentTimeMillis();
            btEngine.run(myBacktest);
            endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        }
    }
}
