package atsys.backtesting.engine.components.id;

import java.util.concurrent.atomic.AtomicLong;

public class SeqIdManager extends IdManager {

    private final AtomicLong orderCounter;
    private final AtomicLong tradeCounter;
    private final AtomicLong signalCounter;

    public SeqIdManager(){
        this.orderCounter = new AtomicLong();
        this.tradeCounter = new AtomicLong();
        this.signalCounter= new AtomicLong();
    }

    @Override
    public String generateId(ComponentType type) {
        String result = null;
        switch (type) {
            case ORDER :
                result = String.valueOf(orderCounter.incrementAndGet());
                break;

            case TRADE :
                result = String.valueOf(tradeCounter.incrementAndGet());
                break;
            case SIGNAL:
                result = String.valueOf(signalCounter.incrementAndGet());
        }
        return result;
    }
}