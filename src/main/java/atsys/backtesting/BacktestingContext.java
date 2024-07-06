package atsys.backtesting;


import atsys.backtesting.engine.EventPublisher;
import atsys.backtesting.engine.events.Event;
import atsys.backtesting.model.Backtest;
import lombok.Getter;

public class BacktestingContext {

    @Getter
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

    public void publishEvent(Event event){
        eventPublisher.publishEvent(event);
    }

}
