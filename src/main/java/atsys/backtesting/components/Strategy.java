package atsys.backtesting.components;

/**
 * Strategy Interface - represents a set of classes which will work with 'T' as its TickData.
 * It can load other data types within itself, but the main TickData will be 'T'.
 * @param <T>
 */
public interface Strategy<T extends TickData> extends LifecycleManager {
    void handleTick(T tickData);
}
