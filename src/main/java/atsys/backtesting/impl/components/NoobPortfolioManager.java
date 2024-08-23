package atsys.backtesting.impl.components;

import atsys.backtesting.engine.components.PortfolioManager;
import atsys.backtesting.engine.components.asset.Instrument;
import atsys.backtesting.engine.components.order.OrderFill;
import atsys.backtesting.engine.components.position.Position;
import atsys.backtesting.engine.components.signal.Signal;
import atsys.backtesting.engine.components.signal.SignalType;
import atsys.backtesting.engine.components.trade.Trade;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "NoobPortfolioManager")
public class NoobPortfolioManager extends PortfolioManager {


    @Override
    public void onSignal(Signal signal) {
        Instrument instrument = signal.getInstrument();
        long currPos = context.getPosition(instrument).map(Position::getQuantity).orElse(0L);
        long orderQty = 5;

        if(currPos <= 0 && signal.getSignalType() == SignalType.BUY){
            context.publishOrder(instrument, orderQty);
        }
        else if(currPos > 0 && signal.getSignalType() == SignalType.SELL){
            orderQty *= -1;
            context.publishOrder(instrument, orderQty);
        }
        log.info("tick: {}, processing {}. currQty: {}, newOrder: {}", context.getLastTick().getLastTradedPrice(), signal, currPos, orderQty);
    }

    @Override
    public void onFill(OrderFill fill) {
        log.info("tick: {}, processing fill: {}", context.getLastTick().getLastTradedPrice(), fill);
        orderService.processOrderFill(fill);
    }

    @Override
    public void onTrade(Trade trade) {
        log.info("tick: {}, processing trade: {}", context.getLastTick().getLastTradedPrice(), trade);
        orderService.addTradeToOrder(trade);
    }
}
