package atsys.backtesting;

import atsys.utils.Decimal;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DecimalTest {

    @Test
    void BasicTest()  {
        BigDecimal one = BigDecimal.valueOf(1.0);
        BigDecimal two = BigDecimal.valueOf(1);
        BigDecimal three = BigDecimal.valueOf(1.000110);
        BigDecimal four = BigDecimal.valueOf(1.0001);
        BigDecimal five = BigDecimal.valueOf(1.00001);


        assertNotEquals(one, two);

        Decimal decimal1 = Decimal.valueOf(one);
        Decimal decimal2 = Decimal.valueOf(two);
        Decimal decimal3 = Decimal.valueOf(three);
        Decimal decimal4 = Decimal.valueOf(four);
        Decimal decimal5 = Decimal.valueOf(five);

        assertEquals(decimal1, decimal2);
        assertEquals(decimal2, decimal5);
        assertEquals(decimal3, decimal4);
        assertEquals(decimal4, decimal5);
    }

    @Test
    void TypesTest() {
        BigDecimal one = BigDecimal.valueOf(2);
        double two = 2.00;
        double three = 2.000;
        BigDecimal four = BigDecimal.valueOf(2.0000000000001);

        assertEquals(Decimal.valueOf(one), Decimal.valueOf(two));
        assertEquals(Decimal.valueOf(two), Decimal.valueOf(three));
        assertEquals(Decimal.valueOf(three), Decimal.valueOf(four));

        assertEquals(Decimal.valueOf(2.220), Decimal.valueOf(2.2200));
        assertNotEquals(Decimal.valueOf(2.220), Decimal.valueOf(2.2201));
        assertEquals(Decimal.valueOf(2.000000), Decimal.valueOf(2.0000001));

        assertEquals(Decimal.valueOf("1.0"), Decimal.valueOf(1.0));
        assertEquals(Decimal.valueOf(1), Decimal.valueOf(1.000));
    }

    @Test
    void arithmeticTest() {
        assertEquals(Decimal.valueOf(2.00).add(1), Decimal.valueOf(3));
        assertEquals(Decimal.valueOf(2.00).add(1.0000001), Decimal.valueOf(3));
        assertNotEquals(Decimal.valueOf(2.00).add(1.0001), Decimal.valueOf(3));

        assertEquals(Decimal.valueOf(2.01).multiply(2), Decimal.valueOf(4.02));
        assertEquals(Decimal.valueOf(2.0000000000001).multiply(2), Decimal.valueOf(4.00));

        int test = 1;
        assertTrue(Decimal.valueOf(1.00).equals(test));

        assertTrue(Decimal.valueOf(1.1).add(2.2).equals(3.3000000001));
    }

    @Test
    void equalityTest(){
        assertTrue(Decimal.valueOf(2).equals(2.000));
        assertTrue(Decimal.valueOf(2).equals(BigDecimal.valueOf(2.00000)));
        assertTrue(Decimal.valueOf(2.00000).equals(2));
    }

    @Test
    void multiplyTest(){
        assertTrue(Decimal.valueOf(2).multiply(2.1).equals(4.200));
        assertTrue(Decimal.valueOf(10).divide(3).equals(3.333333));
        assertTrue(Decimal.valueOf(100).divide(30).equals(Decimal.valueOf(1).divide(0.3)));
    }
}