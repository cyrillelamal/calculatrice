package com.lebonbus.calculatrice.Models.Operators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PriorityTest {
    private static final int LOWER = 1;
    private static final int HIGHER = 2 * LOWER;

    @Test
    void lowerPriority() {
        AbstractOperator l = new Addition("foo", LOWER);
        AbstractOperator u = new Multiplication("bar", HIGHER);

        assertTrue(l.compareTo(u) < 0);
    }

    @Test
    void higherPriority() {
        AbstractOperator l = new Addition("foo", LOWER);
        AbstractOperator u = new Multiplication("bar", HIGHER);

        assertTrue(u.compareTo(l) > 0);
    }

    @Test
    void samePriority() {
        AbstractOperator l = new Addition("foo", LOWER);
        AbstractOperator u = new Addition("bar", LOWER);

        assertEquals(u.compareTo(l), 0);
    }
}
