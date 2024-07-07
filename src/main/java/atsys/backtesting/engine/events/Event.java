package atsys.backtesting.engine.events;

import lombok.Getter;

import java.time.Instant;

public abstract class Event {

    @Getter
    private final Instant creationTime;

    Event(){
        this.creationTime = Instant.now();
    }

}
