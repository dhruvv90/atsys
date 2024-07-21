package atsys.backtesting.engine.events;

import atsys.backtesting.engine.components.signal.Signal;
import lombok.Getter;

@Getter
public class SignalEvent extends Event {

    private final Signal signal;

    public SignalEvent(Signal signal){
        this.signal = signal;
    }
}
