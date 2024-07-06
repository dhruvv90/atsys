package atsys.backtesting;


import atsys.backtesting.engine.EventEmitter;
import atsys.backtesting.engine.EventPublisher;
import atsys.backtesting.engine.event.TickEvent;
import atsys.backtesting.engine.listeners.TickEventListener;
import lombok.Getter;

public class BacktestingContext {

    @Getter
    private final Backtest backtest;

    private final EventPublisher eventPublisher;

    BacktestingContext(Backtest backtest, EventPublisher eventPublisher, EventEmitter eventEmitter){
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
