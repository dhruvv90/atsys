package atsys.impl;

import atsys.api.components.Strategy;
import atsys.api.model.Instrument;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class Backtest {
    private final String name;
    private final String description;
    private final List<Instrument> instruments;
    private final double initialCapital;
    private final Instant startDateTime;
    private final Instant endDateTime;
    private final Strategy strategy;
    private Instant createdAt;

    public Backtest(String name,
                    String description, List<Instrument> instruments,
                    double initialCapital, Instant startDateTime, Instant endDateTime, Strategy strategy) {
        this.name = name;
        this.description = description;
        this.instruments = instruments;
        this.initialCapital = initialCapital;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.strategy = strategy;
        this.createdAt = Instant.now();
    }
}
