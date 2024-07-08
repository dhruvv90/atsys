package atsys.backtesting.engine;

import atsys.backtesting.engine.events.Event;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BacktestingReport {

    private final List<Event> eventsTrail;

    public BacktestingReport(){
        this.eventsTrail = new LinkedList<>();
    }

    public List<Event> getEventsTrail() {
        return Collections.unmodifiableList(eventsTrail);
    }

    public void recordEvent(Event e){
        eventsTrail.add(e);
    }
}
