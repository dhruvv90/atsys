package atsys;


import atsys.backtesting.exception.BaseException;
import atsys.backtesting.model.Backtest;
import atsys.backtesting.engine.Backtester;
import atsys.backtesting.components.strategy.DummyStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;


public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws BaseException {
        tryBacktester();
    }

    private static void tryBacktester() throws BaseException {
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
