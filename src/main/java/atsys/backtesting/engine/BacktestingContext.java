package atsys.backtesting.engine;


import atsys.backtesting.model.Backtest;
import lombok.Getter;

@Getter
public class BacktestingContext {

    private final Backtest backtest;

    private final EventPublisher eventPublisher;

    BacktestingContext(Backtest backtest, EventPublisher eventPublisher){
        this.backtest = backtest;
        this.eventPublisher = eventPublisher;

        this.backtest.getStrategy().onInit(this);
    }


    public void destroy() {
        backtest.getStrategy().onComplete();
    }

}
