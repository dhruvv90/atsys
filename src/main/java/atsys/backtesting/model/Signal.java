package atsys.backtesting.model;

import lombok.Getter;

@Getter
public class Signal {
    private final String symbol;
    private final SignalType signalType;

    public Signal(String symbol, SignalType signalType) {
        this.symbol = symbol;
        this.signalType = signalType;
    }

    @Override
    public String toString() {
        return String.join(" ", this.getClass().getSimpleName(), "(", symbol, ",", signalType.toString(), ")");

    }
}
