package com.lebonbus.calculatrice.Models.Operators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiplicationTest {
    private Multiplication multiplication;

    @BeforeEach
    void setUp() {
        this.multiplication = new Multiplication("bar", 2);
    }

    @Test
    void conserveLong() {
        final Number left = 123L;
        final Number right = 234L;

        assertTrue(this.getMultiplication().apply(left, right) instanceof Long);
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

        assertTrue(this.getMultiplication().apply(left, right) instanceof Double);
    }

    private Multiplication getMultiplication() {
        return multiplication;
    }
}
