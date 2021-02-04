package com.lebonbus.calculatrice.Models.Operators;

public class Division extends Multiplication {
    public static final String KEY = "\u00F7";

    /**
     * Use the default priority
     */
    public Division() {
        this(Division.KEY, Multiplication.PRIORITY);
    }

    /**
     * {@inheritDoc}
     */
    public Division(String k, int p) {
        super(k, p);
    }

    @Override
    public Number apply(Number left, Number right) {
        return super.apply(left, 1 / right.doubleValue());
    }
}
