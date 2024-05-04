package atsys.events;

import java.time.LocalDateTime;

public abstract class Event {
    private LocalDateTime createdAt;

    Event(){
        this.createdAt = LocalDateTime.now();
    }
}
