package atsys.backtesting.engine.events;

import atsys.backtesting.engine.model.Signal;
import atsys.backtesting.engine.model.SignalType;
import lombok.Getter;

@Getter
public class SignalEvent extends Event {

    private final Signal signal;

    public SignalEvent(String symbol, SignalType signalType){
        signal = new Signal(symbol, signalType);
    }
}
