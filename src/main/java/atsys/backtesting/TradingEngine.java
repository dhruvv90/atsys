package atsys.backtesting;

/**
 * Can be used for Live trading, Backtesting or any Simulation
 */
public interface TradingEngine extends LifecycleManager {
    void run();
}
