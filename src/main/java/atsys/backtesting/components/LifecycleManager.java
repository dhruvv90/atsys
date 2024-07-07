package atsys.backtesting.components;

import atsys.backtesting.engine.BacktestingContext;

/**
 * Represents a resource , which is a reusable and runnable component.
 * It provides lifecycle hooks for multiple stages, to carry out actions at certain states
 */
public interface LifecycleManager {
    void onInit(BacktestingContext context);
    void onComplete();
}
