package atsys.utils;


import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Decimal {

    @Getter
    private final BigDecimal value;
    private static final int DEFAULT_SCALE = 6;
    private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;
    public static final Decimal ZERO = Decimal.valueOf(0);

    // Constructors
    private Decimal(BigDecimal value) {
        this.value = value.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }

    private Decimal(double value) {
        this(BigDecimal.valueOf(value));
    }

    private Decimal(String value) {
        this(new BigDecimal(value));
    }

    private Decimal(int value) {
        this(BigDecimal.valueOf(value));
    }

    // Creating objects through static methods
    public static Decimal valueOf(String value) {
        return new Decimal(value);
    }

    public static Decimal valueOf(int value) {
        return new Decimal(value);
    }

    public static Decimal valueOf(double value) {
        return new Decimal(value);
    }

    public static Decimal valueOf(BigDecimal value) {
        return new Decimal(value);
    }

    // Arithmetic operations
    public Decimal add(Decimal other) {
        return Decimal.valueOf(value.add(other.value));
    }

    public Decimal add(BigDecimal other) {
        return add(Decimal.valueOf(other));
    }

    public Decimal add(double other) {
        return add(Decimal.valueOf(other));
    }

    public Decimal add(int other) {
        return add(Decimal.valueOf(other));
    }

    public Decimal subtract(Decimal other) {
        return Decimal.valueOf(this.value.subtract(other.value));
    }

    public Decimal subtract(BigDecimal other) {
        return subtract(Decimal.valueOf(other));
    }

    public Decimal subtract(double other) {
        return subtract(Decimal.valueOf(other));
    }

    public Decimal subtract(int other) {
        return subtract(Decimal.valueOf(other));
    }

    public Decimal multiply(Decimal multiplier) {
        return Decimal.valueOf(this.value.multiply(multiplier.value));
    }

    public Decimal multiply(BigDecimal multiplier) {
        return multiply(Decimal.valueOf(multiplier));
    }

    public Decimal multiply(double multiplier) {
        return multiply(Decimal.valueOf(multiplier));
    }

    public Decimal multiply(int multiplier) {
        return multiply(Decimal.valueOf(multiplier));
    }

    public Decimal divide(Decimal divisor) {
        return Decimal.valueOf(this.value.divide(divisor.value, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE));
    }

    public Decimal divide(BigDecimal divisor) {
        return divide(Decimal.valueOf(divisor));
    }

    public Decimal divide(double divisor) {
        return divide(Decimal.valueOf(divisor));
    }

    public Decimal divide(int divisor) {
        return divide(Decimal.valueOf(divisor));
    }

    public int compareTo(double other){
        return value.compareTo(Decimal.valueOf(other).value);
    }

    public int compareTo(Decimal other){
        return value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public boolean equals(Integer o) {
        return equals(Decimal.valueOf(o));
    }

    public boolean equals(BigDecimal o) {
        return equals(Decimal.valueOf(o));
    }

    public boolean equals(Double o) {
        return equals(Decimal.valueOf(o));
    }

    public boolean equals(Decimal o) {
        return Objects.equals(value, o.value);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (getClass().equals(o.getClass())) {
            return equals((Decimal)o);
        } else if (Integer.class.equals(o.getClass())) {
            return equals((Integer) o);
        } else if (Double.class.equals(o.getClass())) {
            return equals((Double) o);
        } else if (BigDecimal.class.equals(o.getClass())) {
            return equals((BigDecimal) o);
        }
        return false;

    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
