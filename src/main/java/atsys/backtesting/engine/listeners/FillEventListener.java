package atsys.backtesting.engine.listeners;

import atsys.backtesting.components.PortfolioManager;
import atsys.backtesting.engine.events.FillEvent;

public class FillEventListener implements  EventListener<FillEvent> {
    private final PortfolioManager portfolioManager;

    public FillEventListener(PortfolioManager portfolioManager){
        this.portfolioManager = portfolioManager;
    }

    @Override
    public void onEvent(FillEvent event) {
        portfolioManager.onFill(event.getOrder());
    }
}
