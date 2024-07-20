package atsys.backtesting.engine.components;

import atsys.backtesting.engine.Backtest;
import atsys.backtesting.engine.exception.DataStreamerException;

public interface DataStreamer<T extends TickData>  {
    void initializeForBacktest(Backtest<T> backtest) throws DataStreamerException ;
    boolean hasNext();
    T readData() throws DataStreamerException;
    void reset();
}
