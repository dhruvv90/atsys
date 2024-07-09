package atsys.backtesting;

import atsys.backtesting.components.impl.DummyStrategy;
import atsys.backtesting.components.impl.NoobPortfolioManager;
import atsys.backtesting.components.impl.SimulatedExecutionManager;
import atsys.backtesting.engine.Backtester;
import atsys.backtesting.exception.BaseException;
import atsys.backtesting.model.Backtest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BacktestingApplicationTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void basicTest() throws BaseException {

        Backtester btEngine = new Backtester();

        List<Backtest> backtests = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            backtests.add(new Backtest(
                    "Backtest_" + i, "",
                    new ArrayList<>(),
                    1000,
                    Instant.now(),
                    Instant.now(),
                    new DummyStrategy(), new NoobPortfolioManager(), new SimulatedExecutionManager())
            );
        }
        long startTime;
        long endTime;
        List<Long> times = new ArrayList<>();

        for(Backtest backtest: backtests){
            startTime = System.currentTimeMillis();
            btEngine.run(backtest);
            endTime = System.currentTimeMillis();
            times.add(endTime - startTime);
            System.out.println();
        }

        System.out.println(Arrays.toString(times.toArray()));
    }
}