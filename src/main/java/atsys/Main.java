package atsys;


import atsys.backtesting.Backtester;
import atsys.backtesting.components.impl.SimulatedExecutionManager;
import atsys.backtesting.components.impl.NoobPortfolioManager;
import atsys.backtesting.components.impl.DummyStrategy;
import atsys.backtesting.exception.BaseException;
import atsys.backtesting.model.Backtest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {
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
                new DummyStrategy(), new NoobPortfolioManager(), new SimulatedExecutionManager());

        Backtester btEngine = new Backtester();
        long startTime;
        long endTime;

        List<Long> times = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            startTime = System.currentTimeMillis();
            btEngine.run(myBacktest);
            endTime = System.currentTimeMillis();
            times.add(endTime - startTime);
        }

        System.out.println(Arrays.toString(times.toArray()));
    }
}
