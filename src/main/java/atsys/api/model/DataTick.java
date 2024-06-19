package atsys.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;


@Getter
@Setter
public class DataTick {

    private String mode;
    private boolean tradable;
    private long instrumentToken;
    private double lastTradedPrice;
    private double highPrice;
    private double lowPrice;
    private double openPrice;
    private double closePrice;
    private double change;
    private double lastTradedQuantity;
    private double averageTradePrice;
    private long volumeTradedToday;
    private double totalBuyQuantity;
    private double totalSellQuantity;
    private Instant lastTradedTime;
    private double oi;
    private double oiDayHigh;
    private double oiDayLow;
    private Instant tickTimestamp;
    private Map<String, ArrayList<Depth>> depth;
}
