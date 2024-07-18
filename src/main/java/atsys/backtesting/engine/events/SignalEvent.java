package atsys.backtesting.engine.events;

import atsys.backtesting.model.Signal;
import atsys.backtesting.model.SignalType;
import lombok.Getter;

@Getter
public class SignalEvent extends Event {

    private final Signal signal;

    public SignalEvent(String symbol, SignalType signalType){
        signal = new Signal(symbol, signalType);
    }
}
