package atsys.backtesting.engine.components.id;

public abstract class IdManager {

    public enum ComponentType{
        ORDER,
        TRADE,
        SIGNAL
    }

    public abstract String generateId(ComponentType type);
}
