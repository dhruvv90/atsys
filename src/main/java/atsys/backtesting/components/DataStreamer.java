package atsys.backtesting.components;

import atsys.backtesting.exception.DataStreamerException;
import atsys.backtesting.model.Backtest;

public interface DataStreamer<T extends TickData>  {
    void initializeForBacktest(Backtest<T> backtest) throws DataStreamerException ;
    boolean hasNext();
    T readData() throws DataStreamerException;
    void reset();
}
