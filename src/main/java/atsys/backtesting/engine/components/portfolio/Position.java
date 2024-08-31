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

    // Open Trades
    private final Queue<OpenTrade> openBuyTrades;
    private final Queue<OpenTrade> openSellTrades;

    // Closed / Squared off trades
    private final Queue<ClosedTrade> closedTrades;

    private long quantity;
    private Decimal avgPrice;
    private Decimal ltp;

    public Position(Instrument instrument) {
        this.instrument = instrument;
        this.openBuyTrades = new LinkedList<>();
        this.openSellTrades = new LinkedList<>();

        this.closedTrades = new LinkedList<>();
        this.quantity = 0;

        this.avgPrice = Decimal.ZERO;
        this.ltp = Decimal.ZERO;
    }


    /**
     * Calculated lazily - simply by avgPrice, ltp and qty
     */
    public Decimal getUnrealizedPnl() {
        Decimal unrealizedPnl;

        if (quantity == 0) {
            unrealizedPnl = Decimal.ZERO;

        } else if (quantity > 0) {
            // Long position
            unrealizedPnl = ltp.subtract(avgPrice).multiply(quantity);
        } else {
            // Short position
            unrealizedPnl = avgPrice.subtract(ltp).multiply(-quantity);
        }
        return unrealizedPnl;
    }

    /**
     * Calculated lazily by iterating through all Closed trades
     */
    public Decimal getRealizedPnl() {
        Decimal realizedPnl = Decimal.ZERO;
        if(closedTrades.isEmpty()){
            return realizedPnl;
        }
        for (ClosedTrade closedTrade : closedTrades) {
            realizedPnl = realizedPnl.add(closedTrade.getPnl());
        }
        return realizedPnl;
    }

    public void update(Trade trade) {
        this.ltp = trade.getExecutedPrice();

        if (trade.isBuy()) {
            if (openSellTrades.isEmpty()) {
                processTradeRolling(trade);
            } else {
                processTradeSquareOff(trade);
            }
        } else {
            if (openBuyTrades.isEmpty()) {
                processTradeRolling(trade);
            } else {
                processTradeSquareOff(trade);
            }
        }
        storeTrade(trade);
    }

    private void storeTrade(Trade trade) {
        OpenTrade openTrade = new OpenTrade(trade.getInstrument(), trade.getQuantity(),
                trade.getExecutedPrice(), trade.isBuy());
        if (trade.isBuy()) {
            openBuyTrades.add(openTrade);
        } else {
            openSellTrades.add(openTrade);
        }
    }


    private void squareOffSellTrade(Trade sellTrade) {
        if (openBuyTrades.isEmpty()) {
            log.warn("No open buy trades found for incoming sellTrade");
            return;
        }

        long incomingQty = sellTrade.getQuantity(); // -ve
        while (!openBuyTrades.isEmpty() && incomingQty != 0) {
            OpenTrade openBuyTrade = openBuyTrades.peek();
            long qtyToSell = Math.min(Math.abs(openBuyTrade.getQuantity()), Math.abs(incomingQty));

            // SquareOff and record ClosedTrade
            ClosedTrade closedTrade = new ClosedTrade(instrument, qtyToSell, openBuyTrade.getExecutedPrice(),
                    sellTrade.getExecutedPrice());
            closedTrades.offer(closedTrade);

            incomingQty += qtyToSell;
            openBuyTrade.setQuantity(openBuyTrade.getQuantity() - qtyToSell);
            if (openBuyTrade.getQuantity() == 0) {
                openBuyTrades.poll();
            }
        }
    }

    private void squareOffBuyTrade(Trade buyTrade) {
        if (openSellTrades.isEmpty()) {
            log.warn("No open sell trades found for incoming sellTrade");
            return;
        }

        long incomingQty = buyTrade.getQuantity(); // +ve
        while (!openSellTrades.isEmpty() && incomingQty != 0) {
            OpenTrade openSellTrade = openSellTrades.peek();
            long qtyToBuy = Math.min(Math.abs(openSellTrade.getQuantity()), Math.abs(incomingQty));

            // SquareOff and record ClosedTrade
            ClosedTrade closedTrade = new ClosedTrade(instrument, qtyToBuy, buyTrade.getExecutedPrice(),
                    openSellTrade.getExecutedPrice());
            closedTrades.offer(closedTrade);

            incomingQty -= qtyToBuy;
            openSellTrade.setQuantity(openSellTrade.getQuantity() + qtyToBuy);
            if (openSellTrade.getQuantity() == 0) {
                openSellTrades.poll();
            }
        }
    }


    private void processTradeSquareOff(Trade trade) {
        if (trade.isBuy()) {
            squareOffBuyTrade(trade);
        } else {
            squareOffSellTrade(trade);
        }
        quantity += trade.getQuantity();
        avgPrice = recalcAvgPrice();
    }


    /**
     * Does averaging of buy price in same direction and updates it :
     * avg up: curr long , buy trade OR
     * avg down : when curr short, sell trade
     * MUST NOT BE USED IF current position is opposite to side of trade. for that
     * we need to square off and recalculate buy prices
     *
     * @param trade Incoming trade
     */
    private void processTradeRolling(Trade trade) {
        if ((quantity > 0 && !trade.isBuy()) || (quantity < 0 && trade.isBuy())) {
            log.error("Invalid method call : cannot calculate avgPrice for opposite directions. Use squareOff()");
            return;
        }
        Decimal portfolioValue = avgPrice
                .multiply(quantity)
                .add(trade.getTradeValue());

        this.quantity += trade.getQuantity();
        this.avgPrice = portfolioValue.divide(this.quantity);
    }

    /**
     * Recalculate avg price by iterating through all posTrades.
     * Useful after square-off
     */
    private Decimal recalcAvgPrice() {
        Decimal tradeValue = Decimal.ZERO;
        long qty = 0;
        for (OpenTrade trade : openBuyTrades) {
            qty += trade.getQuantity();
            tradeValue = tradeValue.add(trade.getTradeValue());
        }
        for (OpenTrade trade : openSellTrades) {
            qty += trade.getQuantity();
            tradeValue = tradeValue.add(trade.getTradeValue());
        }
        if (qty == 0) {
            return Decimal.ZERO;
        }
        return tradeValue.divide(qty);
    }


}
