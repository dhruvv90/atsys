package atsys.backtesting;

import atsys.backtesting.components.LifecycleManager;
import atsys.backtesting.components.strategy.Strategy;
import atsys.backtesting.model.Instrument;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class Backtest implements LifecycleManager {
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

    @Override
    public void onInit() {
        strategy.onInit();
    }

    @Override
    public void onComplete() {
        strategy.onComplete();
    }
}
