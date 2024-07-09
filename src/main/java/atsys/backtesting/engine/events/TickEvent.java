package atsys.backtesting.engine.events;

import atsys.backtesting.model.SimpleTickData;
import lombok.Getter;

@Getter
public class TickEvent extends Event {

    private final SimpleTickData data;

    public TickEvent(SimpleTickData data){
        this.data = data;
    }
}
