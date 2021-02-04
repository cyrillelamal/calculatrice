package com.lebonbus.calculatrice.Models.Operators;

public class Multiplication extends AbstractOperator {
    public static final String KEY = "\u00D7";
    public static final int PRIORITY = 2;

    /**
     * Use the default priority.
     */
    public Multiplication() {
        this(Multiplication.KEY, Multiplication.PRIORITY);
    }

    /**
     * {@inheritDoc}
     */
    public Multiplication(String k, int p) {
        super(k, p);
    }

    @Override
    public Number apply(Number left, Number right) {
        Number res;

        try {
            if (left instanceof Long && right instanceof Long)
                res = Math.multiplyExact(left.longValue(), right.longValue());
            else res = left.doubleValue() * right.doubleValue();
        } catch (ArithmeticException ignored) {
            res = left.doubleValue() * right.doubleValue();
        }

        return res;
    }
}
