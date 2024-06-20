package atsys.impl.components;

import atsys.api.model.TickData;
import atsys.impl.BacktestingInputs;
import atsys.impl.model.BaseTickData;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataHelper {

    static List<TickData> generateDummyData(BacktestingInputs inputs){
        Instant from = inputs.getStartDateTime();
        Instant to = inputs.getEndDateTime();

        List<TickData> result = new ArrayList<>();
        for(int i = 0; i < 1000; i++){
            BaseTickData data = new BaseTickData();
            data.setOi(1.223);
            data.setTickTimestamp(Instant.now());
            data.setLastTradedTime(Instant.now());
            data.setLastTradedPrice(Math.random() + Math.random() * 100);
            result.add(data);
        }
        return result;
    }
}
