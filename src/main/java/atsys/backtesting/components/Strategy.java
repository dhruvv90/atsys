package atsys.backtesting.components;

import atsys.backtesting.engine.events.SignalEvent;
import atsys.backtesting.model.SignalType;

/**
 * Strategy Interface - represents a set of classes which will work with 'T' as its TickData.
 * It can load other data types within itself, but the main TickData will be 'T'.
 * @param <T>
 */
public abstract class Strategy<T extends TickData> extends LifecycleManager {
    public abstract void handleTick(T tickData);

    protected void publishBuySignal(String symbol){
        context.publishEvent(new SignalEvent(symbol, SignalType.BUY));
    }

    protected void publishSellSignal(String symbo){
        context.publishEvent(new SignalEvent(symbo, SignalType.SELL));
    }


}
