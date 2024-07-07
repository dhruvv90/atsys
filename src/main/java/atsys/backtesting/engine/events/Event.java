package atsys.backtesting.engine.events;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
public abstract class Event {

    private final Instant createdAt;

    @Setter
    private Instant publishedAt;

    @Setter
    private Instant consumedAt;

    Event(){
        this.createdAt = Instant.now();
    }

}
