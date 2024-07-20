package atsys.backtesting;

import atsys.backtesting.engine.components.DataStreamer;
import atsys.backtesting.impl.components.NseHistDataStreamer;
import atsys.backtesting.impl.components.SimpleDataStreamer;
import atsys.backtesting.impl.components.SimpleTickData;
import atsys.backtesting.impl.components.*;
import atsys.backtesting.impl.strategies.DummyStrategy;
import atsys.backtesting.impl.strategies.SmaStrategy;
import atsys.backtesting.engine.Backtester;
import atsys.backtesting.engine.exception.BaseException;
import atsys.backtesting.engine.model.Backtest;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BaseTest {
    private final Backtester backtester = new Backtester();
    long startTime, endTime;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void basicTest() throws BaseException {
        DataStreamer<SimpleTickData> dataStreamer = new SimpleDataStreamer();

        // Setup Backtests
        List<Backtest<SimpleTickData>> backtests = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            backtests.add(new Backtest<>(
                    "Backtest_" + i, "",
                    new ArrayList<>(),
                    1000,
                    Instant.now(),
                    Instant.now(),
                    DummyStrategy.class, NoobPortfolioManager.class, SimulatedExecutionManager.class)
            );
        }

        // Run Backtest in batch
        List<Long> times = new ArrayList<>();

        for(Backtest<SimpleTickData> backtest: backtests){
            startTime = System.currentTimeMillis();
            backtester.run(backtest, dataStreamer);
            endTime = System.currentTimeMillis();
            times.add(endTime - startTime);
            System.out.println();
        }

        // Print output
        System.out.println(Arrays.toString(times.toArray()));
    }


    @org.junit.jupiter.api.Test
    void NseHistoricalTest() throws BaseException {
        DataStreamer<SimpleTickData> dataStreamer = new NseHistDataStreamer();


        // Setup Backtests
        List<Backtest<SimpleTickData>> backtests = new ArrayList<>();

        for(int i = 0; i < 1; i++){
            backtests.add(new Backtest<>(
                    "Backtest_" + i, "",
                    new ArrayList<>(),
                    1000,
                    Instant.now(),
                    Instant.now(),
                    DummyStrategy.class)
            );
        }

        // Run Backtest in batch
        long startTime;
        long endTime;
        List<Long> times = new ArrayList<>();

        for(Backtest<SimpleTickData> backtest: backtests){
            startTime = System.currentTimeMillis();
            backtester.run(backtest, dataStreamer);
            endTime = System.currentTimeMillis();
            times.add(endTime - startTime);
            System.out.println();
        }

        // Print output
        System.out.println(Arrays.toString(times.toArray()));
    }

    @Test
    void SmaStrategyTest() throws BaseException {
        DataStreamer<SimpleTickData> dataStreamer = new NseHistDataStreamer();

        // Setup Backtests
        Backtest<SimpleTickData> backtest =  new Backtest<>(
                "Bt SmaStrategy", "",
                new ArrayList<>(),
                1000,
                Instant.now(),
                Instant.now(),
                SmaStrategy.class);

        // Run Backtest in batch
        long startTime = System.currentTimeMillis();
        backtester.run(backtest, dataStreamer);
        long endTime = System.currentTimeMillis();
        // Print output
        System.out.println(endTime - startTime);
    }
}