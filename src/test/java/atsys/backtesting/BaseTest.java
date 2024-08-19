package atsys.backtesting;

import atsys.backtesting.engine.Backtest;
import atsys.backtesting.engine.Backtester;
import atsys.backtesting.engine.components.DataStreamer;
import atsys.backtesting.engine.exception.BaseException;
import atsys.backtesting.impl.components.NseHistDataStreamer;
import atsys.backtesting.impl.components.SimpleDataStreamer;
import atsys.backtesting.impl.components.SimpleTickData;
import atsys.backtesting.impl.strategies.DummyStrategy;
import atsys.backtesting.impl.strategies.SmaStrategy;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BaseTest {
    private final Backtester backtester = new Backtester();

    @Test
    void basicTest() throws BaseException {
        long startTime, endTime;

        DataStreamer<SimpleTickData> dataStreamer = new SimpleDataStreamer();

        // Setup Backtests
        List<Backtest<SimpleTickData>> backtests = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            backtests.add(new Backtest<>(
                    "Backtest_" + i, "",
                    "sym",
                    1000,
                    Instant.now(),
                    Instant.now(),
                    DummyStrategy.class, dataStreamer)
            );
        }

        // Run Backtest in batch
        List<Long> times = new ArrayList<>();

        for(Backtest<SimpleTickData> backtest: backtests){
            startTime = System.currentTimeMillis();
            backtester.runBacktest(backtest);
            endTime = System.currentTimeMillis();
            times.add(endTime - startTime);
            System.out.println();
        }

        // Print output
        System.out.println(Arrays.toString(times.toArray()));
    }


    @Test
    void NseHistoricalTest() throws BaseException {
        DataStreamer<SimpleTickData> dataStreamer = new NseHistDataStreamer();


        // Setup Backtests
        List<Backtest<SimpleTickData>> backtests = new ArrayList<>();

        for(int i = 0; i < 1; i++){
            backtests.add(new Backtest<>(
                    "Backtest_" + i, "",
                    "sym",
                    1000,
                    Instant.now(),
                    Instant.now(),
                    DummyStrategy.class, dataStreamer)
            );
        }

        // Run Backtest in batch
        long startTime;
        long endTime;
        List<Long> times = new ArrayList<>();

        for(Backtest<SimpleTickData> backtest: backtests){
            startTime = System.currentTimeMillis();
            backtester.runBacktest(backtest);
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
                "sym",
                1000,
                Instant.now(),
                Instant.now(),
                SmaStrategy.class, dataStreamer);

        // Run Backtest in batch
        long startTime = System.currentTimeMillis();
        backtester.runBacktest(backtest);
        long endTime = System.currentTimeMillis();
        // Print output
        System.out.println(endTime - startTime);
    }
}