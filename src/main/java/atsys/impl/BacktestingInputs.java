package atsys.impl;

import atsys.api.model.Instrument;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Builder
public class BacktestingInputs {
    private List<Instrument> instruments;
    private BigDecimal initialCapital;
    private Instant startDateTime;
    private Instant endDateTime;
}
