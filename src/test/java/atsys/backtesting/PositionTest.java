package atsys.backtesting;

import atsys.backtesting.engine.components.asset.Equity;
import atsys.backtesting.engine.components.asset.Instrument;
import atsys.backtesting.engine.components.portfolio.Position;
import atsys.backtesting.engine.components.portfolio.Trade;
import atsys.utils.Decimal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PositionTest {

    @Test
    void BasicTest()  {
        Instrument instrument = new Equity("test");
        Position position = new Position(instrument);
        Trade buy1 = new Trade("1","1", instrument, 10L, Decimal.valueOf(10));
        position.update(buy1);

        assertEquals(10L, position.getQuantity());
        assertTrue(position.getAvgPrice().equals(10));
        assertTrue(position.getRealizedPnl().equals(0));
        assertTrue(position.getUnrealizedPnl().equals(0));

        Trade buy2 = new Trade("2","1", instrument, 10L, Decimal.valueOf(20));
        position.update(buy2);

        assertTrue(position.getAvgPrice().equals(15));
        assertEquals(20L, position.getQuantity());
        assertTrue(position.getRealizedPnl().equals(0));
        assertTrue(position.getUnrealizedPnl().equals(100));
    }

    @Test
    void SellOnLong(){
        Instrument instrument = new Equity("test");
        Position position = new Position(instrument);
        Trade buy1 = new Trade("1","1", instrument, 10L, Decimal.valueOf(10));
        Trade buy2 = new Trade("2","1", instrument, 10L, Decimal.valueOf(20));

        position.update(buy1);
        position.update(buy2);

        Trade sell1 = new Trade("3","1", instrument, -4L, Decimal.valueOf(20));
        position.update(sell1);

        assertTrue(position.getAvgPrice().equals(16.25));
        assertEquals(16L, position.getQuantity());
        assertTrue(position.getRealizedPnl().equals(40));
        assertTrue(position.getUnrealizedPnl().equals(60));
    }

    @Test
    void SellOnShort(){
        Instrument instrument = new Equity("test");
        Position position = new Position(instrument);
        Trade sell1 = new Trade("1","1", instrument, -10L, Decimal.valueOf(10));
        Trade sell2 = new Trade("2","1", instrument, -10L, Decimal.valueOf(20));
        Trade sell3 = new Trade("3","1", instrument, -4L, Decimal.valueOf(20));

        position.update(sell1);
        position.update(sell2);
        position.update(sell3);

        assertTrue(position.getAvgPrice().equals(15.8333333333));
        assertEquals(-24L, position.getQuantity());
        assertTrue(position.getRealizedPnl().equals(0));
        assertTrue(position.getUnrealizedPnl().equals(-100));
    }

}