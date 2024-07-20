package atsys.backtesting.engine.components;

import atsys.backtesting.engine.exception.DataStreamerException;
import atsys.backtesting.engine.model.Backtest;

public interface DataStreamer<T extends TickData>  {
    void initializeForBacktest(Backtest<T> backtest) throws DataStreamerException ;
    boolean hasNext();
    T readData() throws DataStreamerException;
    void reset();
}
