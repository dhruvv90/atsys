package atsys.backtesting.components.impl;

import atsys.backtesting.components.DataStreamer;
import atsys.backtesting.exception.DataStreamerException;
import atsys.backtesting.model.Backtest;
import atsys.csv.CsvReader;
import atsys.csv.CsvRow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NseHistDataStreamer implements DataStreamer<SimpleTickData> {

    private Iterator<SimpleTickData> iterator;

    private SimpleTickData transform(CsvRow data) {
        SimpleTickData result = new SimpleTickData();
        result.setSymbol(data.getToken(0));
        result.setLastTradedPrice(Double.parseDouble(data.getToken(7)));
//        result.setTickTimestamp(LocalDate.parse(data.getToken(2), DateTimeFormatter.ofPattern("dd-MMM-yyyy"))
//                .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        result.setInstrumentName(data.getToken(0));
        return result;
    }

    @Override
    public void initializeForBacktest(Backtest<SimpleTickData> backtest) throws DataStreamerException {
        try {
            List<SimpleTickData> result = new ArrayList<>();
            var reader = new CsvReader();
            List<CsvRow> csvData = reader.readAll("hdfc_1y_jul24.csv");
            for(int i = 1; i < csvData.size(); i++){
                result.add(transform(csvData.get(i)));
            }
            iterator = result.iterator();
        } catch (IOException e) {
            throw new DataStreamerException();
        }
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public SimpleTickData readData() throws DataStreamerException {
        if(!this.hasNext()){
            throw new DataStreamerException();
        }
        return iterator.next();
    }

    @Override
    public void reset() {

    }
}
