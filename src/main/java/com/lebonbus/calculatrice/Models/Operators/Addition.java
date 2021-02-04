package com.lebonbus.calculatrice.Models.Operators;

public class Addition extends AbstractOperator {
    public static final String KEY = "+";
    public static final int PRIORITY = 1;

    /**
     * Use the default priority.
     */
    public Addition() {
        this(Addition.KEY, Addition.PRIORITY);
    }

    /**
     * {@inheritDoc}
     */
    public Addition(String k, int p) {
        super(k, p);
    }

    @Override
    public Number apply(Number left, Number right) {
        Number res;

        try {
            if (left instanceof Long && right instanceof Long) res = Math.addExact(left.longValue(), right.longValue());
            else res = left.doubleValue() + right.doubleValue();
        } catch (ArithmeticException ignored) {
            res = left.doubleValue() + right.doubleValue();
        }

        return res;
    }
}
