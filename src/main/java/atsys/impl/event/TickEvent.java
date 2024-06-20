package atsys.impl.event;

import atsys.api.core.Event;
import atsys.api.model.TickData;
import lombok.Getter;

@Getter
public class TickEvent implements Event {

    private final TickData data;

    public TickEvent(TickData data){
        this.data = data;
    }
}
