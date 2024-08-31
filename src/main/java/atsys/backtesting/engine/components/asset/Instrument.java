package atsys.backtesting.engine.components.asset;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Getter
@Slf4j
public abstract class Instrument {
    private final String symbol;
    private final String name;
    private final Long id;

    Instrument(Long id, String symbol, String name){
        this.symbol = symbol;
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Instrument[" + getSymbol() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(!getClass().equals(obj.getClass())){
            return false;
        }
        Instrument casted = (Instrument) obj;
        return Objects.equals(this.id, casted.id) &&
                Objects.equals(this.name, casted.name) &&
                Objects.equals(this.symbol, casted.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, symbol);
    }
}
