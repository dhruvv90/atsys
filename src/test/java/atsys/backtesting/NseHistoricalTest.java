package atsys.backtesting;

import atsys.backtesting.components.DataStreamer;
import atsys.backtesting.components.impl.*;
import atsys.backtesting.engine.Backtester;
import atsys.backtesting.exception.BaseException;
import atsys.backtesting.model.Backtest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class NseHistoricalTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void basicTest() throws BaseException {
        // Initialize Data Streamer
        DataStreamer<SimpleTickData> dataStreamer = new NseHistDataStreamer();

        // Initialize Backtester
        Backtester btEngine = new Backtester();

        // Setup Backtests
        List<Backtest<SimpleTickData>> backtests = new ArrayList<>();

        for(int i = 0; i < 1; i++){
            backtests.add(new Backtest<>(
                    "Backtest_" + i, "",
                    new ArrayList<>(),
                    1000,
                    Instant.now(),
                    Instant.now(),
                    new DummyStrategy(), new NoobPortfolioManager(), new SimulatedExecutionManager())
            );
        }

        // Run Backtest in batch
        long startTime;
        long endTime;
        List<Long> times = new ArrayList<>();

        for(Backtest<SimpleTickData> backtest: backtests){
            startTime = System.currentTimeMillis();
            btEngine.run(backtest, dataStreamer);
            endTime = System.currentTimeMillis();
            times.add(endTime - startTime);
            System.out.println();
        }

        // Print output
        System.out.println(Arrays.toString(times.toArray()));
    }
}