import atsys.backtesting.Backtest;
import atsys.backtesting.BtParameters;
import atsys.core.HoldingType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Main {
    public static void main(String[] args) {
       BtParameters params = new BtParameters(
               new BigDecimal("10000"),
               LocalDateTime.now().minus(10, ChronoUnit.DAYS),
               LocalDateTime.now(),
               HoldingType.MIS
       );

        Backtest mybacktest = new Backtest("test", params);
        mybacktest.run();
    }
}