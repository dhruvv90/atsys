package atsys.backtesting;

import atsys.backtesting.components.DataStreamer;
import atsys.backtesting.components.impl.*;
import atsys.backtesting.engine.Backtester;
import atsys.backtesting.exception.BaseException;
import atsys.backtesting.model.Backtest;
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
                    new DummyStrategy(), new NoobPortfolioManager(), new SimulatedExecutionManager())
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
                    new DummyStrategy(), new NoobPortfolioManager(), new SimulatedExecutionManager())
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
                new SmaStrategy(), new NoobPortfolioManager(), new SimulatedExecutionManager());


        // Run Backtest in batch

        long startTime = System.currentTimeMillis();
        backtester.run(backtest, dataStreamer);
        long endTime = System.currentTimeMillis();
        // Print output
        System.out.println(endTime - startTime);
    }
}