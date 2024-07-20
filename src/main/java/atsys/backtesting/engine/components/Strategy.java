package atsys.backtesting.engine.components;

/**
 * Strategy Interface - represents a set of classes which will work with 'T' as its TickData.
 * It can load other data types within itself, but the main TickData will be 'T'.
 * @param <T>
 */
public abstract class Strategy<T extends TickData> extends LifecycleManager {
    public abstract void handleTick(T tickData);
}
