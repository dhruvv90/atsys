package atsys.impl.components;

import atsys.api.model.TickData;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataHelper {

    static List<TickData> generateDummyData(){
        List<TickData> result = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            TickData data = new TickData();
            data.setSymbol("Symbol("+ i + ")");
            data.setTickTimestamp(Instant.now());
            data.setLastTradedTime(Instant.now());
            data.setLastTradedPrice(Math.random() + Math.random() * 100);
            result.add(data);
        }
        return result;
    }
}
