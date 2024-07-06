package atsys.backtesting.engine.listeners;

import atsys.backtesting.components.portfolio.PortfolioManager;
import atsys.backtesting.engine.events.SignalEvent;

public class SignalEventListener implements  EventListener<SignalEvent> {

    private PortfolioManager portfolioManager;

    public SignalEventListener(PortfolioManager portfolioManager){
        this.portfolioManager = portfolioManager;
    }

    @Override
    public void onEvent(SignalEvent event) {
        this.portfolioManager.onSignal(event);
    }
}
