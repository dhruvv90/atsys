package atsys;


import atsys.backtesting.Backtest;
import atsys.backtesting.Backtester;
import atsys.backtesting.components.DummyStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.util.ArrayList;


@SpringBootApplication
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
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
