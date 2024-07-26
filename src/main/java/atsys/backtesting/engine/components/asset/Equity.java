package atsys.backtesting.engine.components.asset;


public class Equity extends Instrument{
    private static final Long id = 1L;

    public Equity(String symbol, String name) {
        super(id, symbol, name);
    }

    public Equity(String symbol){
        this(symbol, "");
    }

    @Override
    public String toString() {
        return "EQ[" + getSymbol() + "]";
    }
}
