package atsys.backtesting.engine.components.portfolio;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.utils.Decimal;
import atsys.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PositionTrade {

    private final Instrument instrument;
    private final boolean isBuy;
    private final Decimal executedPrice;

    @Setter
    private long quantity;

    // Constructor managed by the Builder
    public PositionTrade(
            Instrument instrument,
            long quantity,
            Decimal executedPrice,
            boolean isBuy) {
        this.instrument = instrument;
        this.quantity = quantity;
        this.executedPrice = executedPrice;
        this.isBuy = isBuy;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "{"
                + ",sym=" + instrument
                + ",type=" + StringUtils.getDirectionText(isBuy)
                + ",qty=" + quantity
                + ",price=" + executedPrice
                + "}";
    }

}
