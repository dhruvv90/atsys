package atsys.backtesting.engine.events;

import atsys.backtesting.engine.components.TickData;
import lombok.Getter;

@Getter
public class TickEvent<T extends TickData> extends Event {

    private final T data;

    public TickEvent(T data){
        this.data = data;
    }
}
