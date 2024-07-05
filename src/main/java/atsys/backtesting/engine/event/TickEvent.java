package atsys.backtesting.engine.event;

import atsys.backtesting.model.TickData;
import lombok.Getter;

@Getter
public class TickEvent implements Event {

    private final TickData data;

    public TickEvent(TickData data){
        this.data = data;
    }
}
