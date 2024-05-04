package atsys.core;

public interface DataFetcher {

    Boolean continueSession();
    void updateCurrentTime();
    TickData fetchData(String symbol);

}
