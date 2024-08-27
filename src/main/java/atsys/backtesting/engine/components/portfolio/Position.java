package atsys.backtesting.engine.components.portfolio;

import atsys.backtesting.engine.components.asset.Instrument;
import atsys.utils.Decimal;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;

@Getter
@Slf4j
public class Position {
    private final Instrument instrument;
    private final Queue<PositionTrade> buyPosTrades;
    private final Queue<PositionTrade> sellPosTrades;
    private final Queue<PositionTrade> squaredOffTrades;
    private long quantity;
    private Decimal avgPrice;
    private Decimal unrealizedPnl;
    private Decimal realizedPnl;
    private Decimal ltp;


    public Position(Instrument instrument) {
        this.instrument = instrument;
        this.buyPosTrades = new LinkedList<>();
        this.sellPosTrades = new LinkedList<>();

        this.squaredOffTrades = new LinkedList<>();
        this.quantity = 0;

        this.avgPrice = Decimal.ZERO;
        this.unrealizedPnl = Decimal.ZERO;
        this.realizedPnl = Decimal.ZERO;
        this.ltp = Decimal.ZERO;
    }

    public void update(Trade trade) {
        this.ltp = trade.getExecutedPrice();

        if (trade.isBuy()) {
            if (sellPosTrades.isEmpty()) {
                calcAvgPriceUnidirectional(trade);
            } else {
                doSquareOff(trade);
            }
        } else {
            if (buyPosTrades.isEmpty()) {
                calcAvgPriceUnidirectional(trade);
            } else {
                doSquareOff(trade);
            }
        }
        saveTradeInternal(trade);
        refreshUnrealizedPnl();
    }

    private void saveTradeInternal(Trade trade){
        PositionTrade posTrade = new PositionTrade(trade.getInstrument(), trade.getQuantity(),
                trade.getExecutedPrice(), trade.isBuy());
        if(trade.isBuy()){
            buyPosTrades.add(posTrade);
        }
        else{
            sellPosTrades.add(posTrade);
        }
    }


    private void squareOffSellTrade(Trade sellTrade){
        if(buyPosTrades.isEmpty()){
            log.warn("No buy posTrades found for incoming sellTrade");
            return;
        }

        long incomingQty = sellTrade.getQuantity(); // -ve
        while(!buyPosTrades.isEmpty() && incomingQty != 0){
            PositionTrade buyPosTrade = buyPosTrades.peek();
            long qtyToSell = Math.min(Math.abs(buyPosTrade.getQuantity()), Math.abs(incomingQty));

            Decimal pnl = sellTrade.getExecutedPrice().subtract(buyPosTrade.getExecutedPrice()).multiply(qtyToSell);
            // record squared off trade here..

            // update realized pnl
            realizedPnl = realizedPnl.add(pnl);

            incomingQty += qtyToSell;
            buyPosTrade.setQuantity(buyPosTrade.getQuantity() - qtyToSell);
            if(buyPosTrade.getQuantity() == 0){
                buyPosTrades.poll();
            }
        }
    }

    private void squareOffBuyTrade(Trade buyTrade){
        if(sellPosTrades.isEmpty()){
            log.warn("No sell posTrades found for incoming sellTrade");
            return;
        }

        long incomingQty = buyTrade.getQuantity(); // +ve
        while(!sellPosTrades.isEmpty() && incomingQty != 0){
            PositionTrade sellPosTrade = sellPosTrades.peek();
            long qtyToBuy = Math.min(Math.abs(sellPosTrade.getQuantity()), Math.abs(incomingQty));

            Decimal pnl = sellPosTrade.getExecutedPrice().subtract(buyTrade.getExecutedPrice()).multiply(qtyToBuy);
            // record squared off trade here..

            // update realized pnl
            realizedPnl = realizedPnl.add(pnl);

            incomingQty -= qtyToBuy;
            sellPosTrade.setQuantity(sellPosTrade.getQuantity() + qtyToBuy);
            if(sellPosTrade.getQuantity() == 0){
                sellPosTrades.poll();
            }
        }
    }



    private void doSquareOff(Trade trade) {
        if(trade.isBuy()){
            squareOffBuyTrade(trade);
        }
        else{
            squareOffSellTrade(trade);
        }
        quantity += trade.getQuantity();
        avgPrice = recalcAvgPrice();
    }


    /**
     * Does averaging of buy price in same direction and updates it :
     *  avg up: curr long , buy trade OR
     *  avg down : when curr short, sell trade
     * MUST NOT BE USED IF current position is opposite to side of trade. for that
     * we need to square off and recalculate buy prices
     *
     * @param trade Incoming trade
     */
    private void calcAvgPriceUnidirectional(Trade trade) {
        if((quantity > 0 && !trade.isBuy()) || (quantity < 0 && trade.isBuy())){
            log.error("Invalid method call : cannot calculate avgPrice for opposite directions. Use squareOff()");
            return;
        }
        Decimal portfolioValue = avgPrice
                .multiply(quantity)
                .add(trade.getTradeValue());

        this.quantity += trade.getQuantity();
        this.avgPrice = portfolioValue.divide(Decimal.valueOf(this.quantity));
    }

    /**
     * Recalculate avg price by iterating through all posTrades.
     * Useful after square-off
     */
    private Decimal recalcAvgPrice(){
        Decimal tradeValue = Decimal.ZERO;
        long qty = 0;
        for(PositionTrade trade: buyPosTrades){
            qty += trade.getQuantity();
            tradeValue = tradeValue.add(trade.getTradeValue());
        }
        for(PositionTrade trade: sellPosTrades){
            qty += trade.getQuantity();
            tradeValue = tradeValue.add(trade.getTradeValue());
        }
        if(qty == 0){
            return Decimal.ZERO;
        }
        return tradeValue.divide(qty);
    }

    /**
     * Refresh Unrealized PnL using CURRENT ltp and avgPrice.
     * Can be used to refresh unrealized pnl after trade updates qty/price
     */
    private void refreshUnrealizedPnl() {
        if (quantity == 0) {
            unrealizedPnl = Decimal.ZERO;

        } else if (quantity > 0) {
            // Long position
            unrealizedPnl = ltp.subtract(avgPrice).multiply(quantity);
        } else {
            // Short position
            unrealizedPnl = avgPrice.subtract(ltp).multiply(-quantity);
        }
    }
}
