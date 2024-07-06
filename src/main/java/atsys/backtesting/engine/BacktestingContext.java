package atsys.backtesting.engine;


import atsys.backtesting.model.Backtest;
import lombok.Getter;

public class BacktestingContext {

    @Getter
    private final Backtest backtest;

    private final EventPublisher eventPublisher;

    BacktestingContext(Backtest backtest, EventPublisher eventPublisher){
        this.backtest = backtest;
        this.eventPublisher = eventPublisher;
    }

    public void initialize() {
        // Initialize Strategy
        backtest.getStrategy().onInit(this);
    }

    public void complete() {
        backtest.getStrategy().onComplete();
    }
}
