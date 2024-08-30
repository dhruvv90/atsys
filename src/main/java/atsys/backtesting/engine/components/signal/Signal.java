package atsys.backtesting.engine.components.signal;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.utils.StringUtils;
import lombok.Getter;

@Getter
public class Signal {
    private final String id;
    private final Instrument instrument;
    private final boolean isBuy;

    public Signal(String id, Instrument instrument, boolean isBuy) {
        this.instrument = instrument;
        this.isBuy = isBuy;
        this.id = id;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + " {"
                + "id="+id
                + ", "+instrument
                + ", " + StringUtils.getDirectionText(isBuy)
                + "}";
    }
}
