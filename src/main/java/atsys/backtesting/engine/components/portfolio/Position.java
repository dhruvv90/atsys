package atsys.backtesting.engine.components.portfolio;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.utils.Decimal;
import lombok.Getter;

import java.util.LinkedList;
import java.util.Queue;

@Getter
public class Position {
    private final Instrument instrument;
    private final Queue<PositionTrade> buyPosTrades;
    private final Queue<PositionTrade> sellPosTrades;
    private final Queue<PositionTrade> squaredOffTrades;
    private long quantity;
    private Decimal avgBuyPrice;
    private Decimal unrealizedPnl;
    private Decimal realizedPnl;
    private Decimal percentageChange;


    Position(Instrument instrument){
        this.instrument = instrument;
        this.buyPosTrades = new LinkedList<>();
        this.sellPosTrades = new LinkedList<>();
        this.squaredOffTrades = new LinkedList<>();
        this.quantity = 0;

        this.avgBuyPrice = Decimal.ZERO;
        this.unrealizedPnl = Decimal.ZERO;
        this.realizedPnl = Decimal.ZERO;
        this.percentageChange = Decimal.ZERO;
    }

    public void update(Trade trade){

    }
}
