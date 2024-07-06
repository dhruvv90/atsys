package atsys.backtesting.components.portfolio;

import atsys.backtesting.components.LifecycleManager;
import atsys.backtesting.engine.events.SignalEvent;

public interface PortfolioManager extends LifecycleManager {

    void onSignal(SignalEvent event);
}
