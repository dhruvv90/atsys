package atsys.backtesting.engine;

import atsys.backtesting.engine.exception.BaseException;
import lombok.extern.slf4j.Slf4j;


/**
 * A Re-usable Event Driven Backtester used to run backtests independently.
 * Engine, Streamer and other components must be reset after each backtest
 */
@Slf4j(topic = "Setup")
public class Backtester {

    /**
     * Run Backtest independently
     */
    public void runBacktest(Backtest<?> backtest) throws BaseException {
        EventDrivenEngine engine = new EventDrivenEngine(backtest);
        engine.run();
    }
}
