package atsys.backtesting.components.impl;

import atsys.backtesting.model.Backtest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataHelper {

    public static List<SimpleTickData> generateDummyData(Backtest<SimpleTickData> backtest){
        List<SimpleTickData> result = new ArrayList<>();
        for(int i = 0; i < 15; i++){
            SimpleTickData data = new SimpleTickData();
            data.setSymbol("Symbol("+ i + ")");
            data.setTickTimestamp(Instant.now());
            data.setLastTradedTime(Instant.now());
            data.setLastTradedPrice(Math.random() + Math.random() * 100);
            result.add(data);
        }
        return result;
    }
}
