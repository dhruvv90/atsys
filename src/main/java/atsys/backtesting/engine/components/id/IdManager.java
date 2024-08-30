package atsys.backtesting.engine.components.id;

public abstract class IdManager {

    public enum ComponentType{
        ORDER,
        TRADE,
        SIGNAL,
        CLOSED_TRADE
    }

    public abstract String generateId(ComponentType type);
}
