package atsys.backtesting.engine;

import java.util.concurrent.atomic.AtomicLong;

public class OrderIdGenerator {
    private static final AtomicLong counter = new AtomicLong();

    public static long generateId(){
        return counter.incrementAndGet();
    }
}
