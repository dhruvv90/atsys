package atsys.backtesting.components;

import atsys.backtesting.engine.events.FillEvent;
import atsys.backtesting.engine.events.SignalEvent;

public abstract class PortfolioManager extends LifecycleManager {

    public abstract void onSignal(SignalEvent event);
    public abstract void onFill(FillEvent event);
}
