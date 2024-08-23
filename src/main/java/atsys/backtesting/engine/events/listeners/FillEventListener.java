package atsys.backtesting.engine.events.listeners;

import atsys.backtesting.engine.components.PortfolioManager;
import atsys.backtesting.engine.events.FillEvent;

public class FillEventListener implements  EventListener<FillEvent> {
    private final PortfolioManager portfolioManager;

    public FillEventListener(PortfolioManager portfolioManager){
        this.portfolioManager = portfolioManager;
    }

    @Override
    public void onEvent(FillEvent event) {
        portfolioManager.onFill(event.getOrderFill());
    }
}
