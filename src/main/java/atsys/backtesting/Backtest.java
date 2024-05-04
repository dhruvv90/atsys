package atsys.backtesting;

import atsys.core.DataFetcher;
import atsys.events.Event;
import atsys.events.EventException;
import atsys.events.EventManager;
import atsys.events.EventQueue;

import java.time.LocalDateTime;

public class Backtest {
    private final BtParameters backtestingParams;
    private final DataFetcher dataFetcher;
    private final String name;
    private final LocalDateTime createdAt;

    private EventQueue eventQueue;
    private EventManager<Event> eventManager;

    public Backtest(String name, BtParameters backtestingParams, DataFetcher dataFetcher) {
        this.name = name;
        this.backtestingParams = backtestingParams;
        this.createdAt = LocalDateTime.now();
        this.dataFetcher = dataFetcher;

        this.eventQueue = new EventQueue();
        this.eventManager = new EventManager<>();
    }

    public void start() throws EventException {
        // Pre checks..
        run();
        // Post-processing;
    }

    private void run() throws EventException {
        while(true){
            if(!dataFetcher.continueSession()){
                break;
            }
            while(!this.eventQueue.isEmpty()){
                Event event = this.eventQueue.get();
                this.eventManager.invoke(event);
            }
        }
    }

    @Override
    public String toString() {
        return "Backtest Name : " + this.name + " created on : " + this.createdAt;
    }

}
