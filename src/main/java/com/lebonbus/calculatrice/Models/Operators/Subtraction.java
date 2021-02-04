package com.lebonbus.calculatrice.Models.Operators;

public class Subtraction extends Addition {
    public static final String KEY = "-";

    /**
     * {@inheritDoc}
     */
    public Subtraction() {
        super(Subtraction.KEY, Addition.PRIORITY);
    }

    @Override
    public Number apply(Number left, Number right) {
        if (right instanceof Long) right = -1 * right.longValue();
        else right = -1 * right.doubleValue();

        return super.apply(left, right);
    }
}
