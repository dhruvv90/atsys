package atsys.backtesting.engine;

import atsys.backtesting.engine.components.ExecutionManager;
import atsys.backtesting.engine.components.PortfolioManager;
import atsys.backtesting.engine.components.Strategy;
import atsys.backtesting.engine.components.TickData;
import atsys.backtesting.impl.components.NoobPortfolioManager;
import atsys.backtesting.impl.components.SimulatedExecutionManager;
import lombok.Getter;

import java.time.Instant;

@Getter
public class Backtest<T extends TickData> {
    private final String name;
    private final String description;
    private final String symbol;
    private final double initialCapital;
    private final Instant startDateTime;
    private final Instant endDateTime;
    private final Instant createdAt;

    // component Classes
    private final Class<? extends ExecutionManager> executionMgrClazz;
    private final Class<? extends Strategy<T>> strategyClazz;
    private final Class<? extends PortfolioManager> portfolioClazz;

    public Backtest(String name,
                    String description, String symbol,
                    double initialCapital, Instant startDateTime, Instant endDateTime,
                    Class<? extends Strategy<T>> strategyClazz,
                     Class<? extends PortfolioManager> portfolioClazz, Class<? extends ExecutionManager> executionMgrClazz) {
        this.name = name;
        this.description = description;
        this.symbol = symbol;
        this.initialCapital = initialCapital;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.strategyClazz = strategyClazz;
        this.createdAt = Instant.now();
        this.portfolioClazz = portfolioClazz;
        this.executionMgrClazz = executionMgrClazz;
    }

    public Backtest(String name,
                    String description, String symbol,
                    double initialCapital, Instant startDateTime, Instant endDateTime,
                    Class<? extends Strategy<T>> strategyClazz) {
        this(name, description, symbol, initialCapital, startDateTime, endDateTime, strategyClazz,
                NoobPortfolioManager.class, SimulatedExecutionManager.class);
    }
}
