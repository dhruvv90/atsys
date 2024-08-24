package atsys.backtesting.engine.components.portfolio;

import atsys.backtesting.engine.components.asset.Instrument;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PositionService {
    private final Map<Instrument, Position> positionMap;

    public PositionService(){
        this.positionMap = new HashMap<>();
    }

    public Optional<Position> getPosition(Instrument instrument){
        Position pos = positionMap.get(instrument);
        if(pos != null){
            return Optional.of(pos);
        }
        return Optional.empty();
    }

    public void addPosition(Instrument instrument, Long quantity){
        if(positionMap.containsKey(instrument)){
            Position pos = positionMap.get(instrument);
            pos.setQuantity(pos.getQuantity() + quantity);
            return;
        }
        Position pos = new Position(instrument);
        pos.setQuantity(quantity);
        positionMap.put(instrument, pos);
    }

}
