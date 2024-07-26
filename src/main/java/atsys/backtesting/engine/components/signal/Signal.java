package atsys.backtesting.engine.components.signal;

import atsys.backtesting.engine.components.asset.Instrument;
import lombok.Getter;

@Getter
public class Signal {
    private final Instrument instrument;
    private final SignalType signalType;

    public Signal(Instrument instrument, SignalType signalType) {
        this.instrument = instrument;
        this.signalType = signalType;
    }

    @Override
    public String toString() {
        return String.join("",
                this.getClass().getSimpleName(),
                "(", instrument.toString(), ",", signalType.toString(), ")");
    }
}
