package atsys.backtesting.engine.components.id;

import java.util.UUID;

public class UuidManager extends IdManager{

    @Override
    public String generateId(ComponentType type) {
        return UUID.randomUUID().toString();
    }
}
