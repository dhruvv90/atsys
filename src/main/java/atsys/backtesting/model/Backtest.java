package atsys.backtesting.model;

import atsys.backtesting.components.portfolio.PortfolioManager;
import atsys.backtesting.components.strategy.Strategy;
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
    private final PortfolioManager portfolioManager;
    private final Instant createdAt;

    public Backtest(String name,
                    String description, List<Instrument> instruments,
                    double initialCapital, Instant startDateTime, Instant endDateTime,
                    Strategy strategy, PortfolioManager portfolioManager) {
        this.name = name;
        this.description = description;
        this.instruments = instruments;
        this.initialCapital = initialCapital;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.strategy = strategy;
        this.createdAt = Instant.now();
        this.portfolioManager = portfolioManager;
    }
}