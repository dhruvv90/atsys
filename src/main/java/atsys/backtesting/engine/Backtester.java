package atsys.backtesting.engine;

import atsys.backtesting.engine.exception.BaseException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A Re-usable Event Driven Backtester used to run backtests independently.
 * Engine, Streamer and other components must be reset after each backtest
 */
@Slf4j(topic = "Setup")
@Getter
public class Backtester {
    private final Map<Backtest<?>, List<BacktestingReport>> reportMap;

    public Backtester() {
        this.reportMap = new HashMap<>();
    }

    /**
     * Run Backtest independently
     */
    public void runBacktest(Backtest<?> backtest) throws BaseException {
        EventDrivenEngine engine = new EventDrivenEngine(backtest);
        engine.run();
        reportMap.putIfAbsent(backtest, new ArrayList<>());
        reportMap.get(backtest).add(engine.getReport());
    }
}
