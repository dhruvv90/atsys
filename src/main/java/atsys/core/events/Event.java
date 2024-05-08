package atsys.core.events;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Event {
    private LocalDateTime createdAt;

    public Event(){
        this.createdAt = LocalDateTime.now();
    }
}
