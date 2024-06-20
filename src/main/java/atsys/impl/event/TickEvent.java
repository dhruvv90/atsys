package atsys.impl.event;

import atsys.api.event.Event;
import atsys.impl.model.BaseTickData;
import lombok.Getter;

@Getter
public class TickEvent implements Event {

    private final BaseTickData data;

    public TickEvent(BaseTickData data){
        this.data = data;
    }
}
