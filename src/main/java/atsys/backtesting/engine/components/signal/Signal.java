package atsys.backtesting.engine.components.signal;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.utils.StringUtils;
import lombok.Getter;

@Getter
public class Signal {
    private final Instrument instrument;
    private final boolean isBuy;

    public Signal(Instrument instrument, boolean isBuy) {
        this.instrument = instrument;
        this.isBuy = isBuy;
    }

    @Override
    public String toString() {
        return String.join("",
                this.getClass().getSimpleName(),
                "(", instrument.toString(), ",", StringUtils.getDirectionText(isBuy), ")");
    }
}
