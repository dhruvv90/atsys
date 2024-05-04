package atsys.backtesting;

import atsys.core.HoldingType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BtParameters {
    private BigDecimal initialCapital;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private HoldingType holdingType;

    public BtParameters(BigDecimal initialCapital, LocalDateTime startDateTime, LocalDateTime endDateTime, HoldingType holdingType) {
        this.initialCapital = initialCapital;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.holdingType = holdingType;
    }

    public BigDecimal getInitialCapital() {
        return initialCapital;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public HoldingType getHoldingType() {
        return holdingType;
    }
}
