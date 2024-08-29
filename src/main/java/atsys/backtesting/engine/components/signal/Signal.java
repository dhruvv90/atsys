package atsys.backtesting.engine.components.signal;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.utils.StringUtils;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicLong;

@Getter
public class Signal {
    private final String signalId;
    private final Instrument instrument;
    private final boolean isBuy;
    private static final AtomicLong counter = new AtomicLong();

    public Signal(Instrument instrument, boolean isBuy) {
        this.instrument = instrument;
        this.isBuy = isBuy;
        this.signalId = String.valueOf(counter.incrementAndGet());
    }

    @Override
    public String toString() {
        return String.join("",
                this.getClass().getSimpleName(),
                "(", instrument.toString(), ",", StringUtils.getDirectionText(isBuy), ")");
    }
}
