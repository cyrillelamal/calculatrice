package com.lebonbus.calculatrice.Models;

import org.junit.jupiter.api.Test;

import static com.lebonbus.calculatrice.Models.Token.Role.OPERAND;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenTest {
    private static final String LONG = "123";
    private static final String DOUBLE = LONG + ".123";

    @Test
    void preferLongToDouble() {
        Token l = new Token(LONG, OPERAND);

        assertTrue(l.toNumber() instanceof Long);
    }

    @Test
    void fallToDouble() {
        Token d = new Token(DOUBLE, OPERAND);

        assertTrue(d.toNumber() instanceof Double);
    }
}
