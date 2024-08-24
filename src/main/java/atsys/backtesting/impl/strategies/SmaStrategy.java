package atsys.backtesting.impl.strategies;

import atsys.backtesting.engine.components.Strategy;
import atsys.backtesting.engine.components.asset.Instrument;
import atsys.backtesting.engine.components.portfolio.Position;
import atsys.backtesting.engine.components.signal.SignalType;
import atsys.backtesting.impl.components.SimpleTickData;
import atsys.utils.Decimal;
import lombok.extern.slf4j.Slf4j;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;


@Slf4j(topic = "Strategy")
public class SmaStrategy extends Strategy<SimpleTickData> {
    private Decimal movingAverage;
    private final Deque<Decimal> storage;
    private static final int PERIOD = 10;

    public SmaStrategy() {
        this.storage = new LinkedList<>();
        this.movingAverage = Decimal.ZERO;
    }

    @Override
    public void handleTick(SimpleTickData tickData) {
        Decimal price = tickData.getLastTradedPrice();
        Instrument instrument = tickData.getInstrument();

        if (storage.size() < PERIOD) {
            storage.addLast(price);
            movingAverage = ((movingAverage.multiply(storage.size() - 1)).add(price))
                    .divide(storage.size());

            log.info("price : " + price + ", no trade!");
            return;
        }
        else{
            movingAverage = ((movingAverage.multiply(storage.size())).subtract(storage.pollFirst()).add(price))
                    .divide((storage.size() + 1));
            storage.addLast(price);
        }

        Optional<Position> currentPos = context.getPosition(instrument);
        long currQty = currentPos.map(Position::getQuantity).orElse(0L);


        if (currQty <= 0 && price.compareTo(movingAverage) >= 0) {
            context.publishSignal(instrument, SignalType.BUY);
        } else if (currQty > 0 && price.compareTo(movingAverage) < 0) {
            context.publishSignal(instrument, SignalType.SELL);
        }
        log.info("price : " + price + ", ma: " + movingAverage);
    }
}
