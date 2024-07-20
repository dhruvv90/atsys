package atsys.backtesting.engine.components.position;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PositionService {
    private final Map<String, Position> positionMap;

    public PositionService(){
        this.positionMap = new HashMap<>();
    }

    public Optional<Position> getPosition(String symbol){
        Position pos = positionMap.get(symbol);
        if(pos != null){
            return Optional.of(pos);
        }
        return Optional.empty();
    }

    public void addPosition(String symbol, Long quantity){
        if(positionMap.containsKey(symbol)){
            Position pos = positionMap.get(symbol);
            pos.setQuantity(pos.getQuantity() + quantity);
            return;
        }
        Position pos = new Position(symbol);
        pos.setQuantity(quantity);
        positionMap.put(symbol, pos);
    }

}
