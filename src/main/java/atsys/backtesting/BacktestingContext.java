package atsys.backtesting;


import atsys.backtesting.engine.EventPublisher;
import atsys.backtesting.model.Backtest;
import lombok.Getter;

@Getter
public class BacktestingContext {

    private final Backtest backtest;

    private final EventPublisher eventPublisher;

    public BacktestingContext(Backtest backtest, EventPublisher eventPublisher){
        this.backtest = backtest;
        this.eventPublisher = eventPublisher;

        this.backtest.getStrategy().onInit(this);
        this.backtest.getPortfolioManager().onInit(this);
    }


    public void destroy() {
        backtest.getStrategy().onComplete();
        backtest.getPortfolioManager().onComplete();
    }

}
