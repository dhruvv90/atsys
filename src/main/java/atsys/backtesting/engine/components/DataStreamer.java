package atsys.backtesting.engine.components;

import atsys.backtesting.engine.Backtest;
import atsys.backtesting.engine.exception.DataStreamingException;
import atsys.backtesting.engine.exception.InitializationException;

public interface DataStreamer<T extends TickData>  {
    void initializeForBacktest(Backtest<T> backtest) throws InitializationException;
    boolean hasNext();
    T readData() throws DataStreamingException;
    void reset();
}
