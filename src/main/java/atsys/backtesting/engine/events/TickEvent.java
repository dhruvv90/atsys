package atsys.backtesting.engine.events;

import atsys.backtesting.model.TickData;
import lombok.Getter;

@Getter
public class TickEvent extends Event {

    private final TickData data;

    public TickEvent(TickData data){
        this.data = data;
    }
}
