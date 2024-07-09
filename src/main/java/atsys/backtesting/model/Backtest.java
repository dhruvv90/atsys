package atsys.backtesting.model;

import atsys.backtesting.components.ExecutionManager;
import atsys.backtesting.components.PortfolioManager;
import atsys.backtesting.components.Strategy;
import atsys.backtesting.components.TickData;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class Backtest<T extends TickData> {
    private final String name;
    private final String description;
    private final List<Instrument> instruments;
    private final double initialCapital;
    private final Instant startDateTime;
    private final Instant endDateTime;
    private final Strategy<T> strategy;
    private final PortfolioManager portfolioManager;
    private final Instant createdAt;
    private final ExecutionManager executionManager;

    public Backtest(String name,
                    String description, List<Instrument> instruments,
                    double initialCapital, Instant startDateTime, Instant endDateTime,
                    Strategy<T> strategy, PortfolioManager portfolioManager, ExecutionManager executionManager) {
        this.name = name;
        this.description = description;
        this.instruments = instruments;
        this.initialCapital = initialCapital;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.strategy = strategy;
        this.createdAt = Instant.now();
        this.portfolioManager = portfolioManager;
        this.executionManager = executionManager;
    }
}
