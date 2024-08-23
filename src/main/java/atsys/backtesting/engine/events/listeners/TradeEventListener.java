package atsys.backtesting.engine.events.listeners;

import atsys.backtesting.engine.components.PortfolioManager;
import atsys.backtesting.engine.events.TradeEvent;

public class TradeEventListener implements  EventListener<TradeEvent> {
    private final PortfolioManager portfolioManager;

    public TradeEventListener(PortfolioManager portfolioManager){
        this.portfolioManager = portfolioManager;
    }

    @Override
    public void onEvent(TradeEvent event) {
        portfolioManager.onTrade(event.getTrade());
    }
}
