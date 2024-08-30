package atsys.backtesting.engine.components.portfolio;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.backtesting.engine.components.id.IdManager;
import atsys.utils.Decimal;

import java.util.ArrayList;
import java.util.List;

public class TradeService {
    private final IdManager idManager;
    private final List<Trade> trades;

    public TradeService(IdManager idManager){
        this.idManager = idManager;
        this.trades = new ArrayList<>();
    }

    public Trade createTrade( String orderId, Instrument instrument, long quantity, Decimal executedPrice){
        String tradeId = idManager.generateId(IdManager.ComponentType.TRADE);
        Trade trade = new Trade(tradeId, orderId, instrument, quantity, executedPrice);
        trades.add(trade);
        return trade;
    }
}
