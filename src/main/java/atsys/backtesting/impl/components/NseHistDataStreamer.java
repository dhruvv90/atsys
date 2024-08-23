package atsys.backtesting.impl.components;

import atsys.backtesting.engine.Backtest;
import atsys.backtesting.engine.components.DataStreamer;
import atsys.backtesting.engine.components.asset.Equity;
import atsys.backtesting.engine.exception.DataStreamingException;
import atsys.backtesting.engine.exception.InitializationException;
import atsys.utils.CsvReader;
import atsys.utils.CsvRow;
import atsys.utils.DatetimeUtils;
import atsys.utils.Decimal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NseHistDataStreamer implements DataStreamer<SimpleTickData> {

    private Iterator<SimpleTickData> iterator;

    private SimpleTickData transform(CsvRow data) {
        SimpleTickData result = new SimpleTickData();
        result.setInstrument(new Equity(data.getToken(0)));
        result.setLastTradedPrice(Decimal.valueOf(data.getToken(7)));
        result.setTickTimestamp(DatetimeUtils.parseInstant(data.getToken(2), "dd-MMM-yyyy"));
        return result;
    }

    @Override
    public void initializeForBacktest(Backtest<SimpleTickData> backtest) throws InitializationException {
        try {
            List<SimpleTickData> result = new ArrayList<>();
            var reader = new CsvReader();
            List<CsvRow> csvData = reader.readAll("hdfc_1y_jul24.csv");
            for(int i = 1; i < csvData.size(); i++){
                result.add(transform(csvData.get(i)));
            }
            iterator = result.iterator();
        } catch (IOException e) {
            throw new InitializationException(e);
        }
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public SimpleTickData readData() throws DataStreamingException {
        if(!this.hasNext()){
            throw new DataStreamingException();
        }
        return iterator.next();
    }
}
