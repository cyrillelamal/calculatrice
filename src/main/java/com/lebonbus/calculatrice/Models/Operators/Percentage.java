package com.lebonbus.calculatrice.Models.Operators;

public class Percentage extends AbstractOperator {
    public static final String KEY = "%";
    public static final int PRIORITY = 2;

    /**
     * {@inheritDoc}
     */
    public Percentage(String k, int p) {
        super(k, p);
    }

    public Percentage() {
        this(Percentage.KEY, Percentage.PRIORITY);
    }

    @Override
    public Number apply(Number left, Number right) {
        return left.doubleValue() * right.doubleValue() / 100;
    }
}
