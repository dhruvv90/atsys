package atsys.backtesting.engine.events.listeners;

import atsys.backtesting.engine.components.PortfolioManager;
import atsys.backtesting.engine.events.OrderFillEvent;

public class OrderFillEventListener implements  EventListener<OrderFillEvent> {
    private final PortfolioManager portfolioManager;

    public OrderFillEventListener(PortfolioManager portfolioManager){
        this.portfolioManager = portfolioManager;
    }

    @Override
    public void onEvent(OrderFillEvent event) {
        portfolioManager.onFill(event.getOrderFill());
    }
}
