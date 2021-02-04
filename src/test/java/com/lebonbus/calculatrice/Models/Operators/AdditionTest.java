package com.lebonbus.calculatrice.Models.Operators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdditionTest {
    private Addition addition;

    @BeforeEach
    void setUp() {
        this.addition = new Addition("foo", 1);
    }

    @Test
    void conserveLong() {
        final Number left = 123L;
        final Number right = 234L;

        assertTrue(this.getAddition().apply(left, right) instanceof Long);
    }

    @Test
    void fallToDouble() {
        Number left, right;
        if (Math.random() >= 0.5) {
            left = 123D;
            right = 123L;
        } else {
            left = 123L;
            right = 123D;
        }

        assertTrue(this.getAddition().apply(left, right) instanceof Double);
    }

    private Addition getAddition() {
        return addition;
    }
}
