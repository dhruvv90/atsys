package atsys.backtesting.engine.events.listeners;

import atsys.backtesting.engine.components.PortfolioManager;
import atsys.backtesting.engine.events.SignalEvent;

public class SignalEventListener implements  EventListener<SignalEvent> {

    private final PortfolioManager portfolioManager;

    public SignalEventListener(PortfolioManager portfolioManager){
        this.portfolioManager = portfolioManager;
    }

    @Override
    public void onEvent(SignalEvent event) {
        this.portfolioManager.onSignal(event.getSignal());
    }
}
