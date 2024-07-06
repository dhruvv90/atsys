package atsys.backtesting.components;

import atsys.backtesting.engine.events.FillEvent;
import atsys.backtesting.engine.events.SignalEvent;

public interface PortfolioManager extends LifecycleManager {

    void onSignal(SignalEvent event);

    void onFill(FillEvent event);
}
