package com.lebonbus.calculatrice.Models;

import com.lebonbus.calculatrice.Exceptions.UnexpectedTokenException;
import com.lebonbus.calculatrice.Models.Operators.AbstractOperator;
import com.lebonbus.calculatrice.Models.Operators.Addition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.lebonbus.calculatrice.Models.Token.Role.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class RPNTest {
    private static final int ADD_P = 1;
    private static final int SUB_P = ADD_P;
    private static final int MUL_P = 2 * ADD_P;

    private static final String ADD = "+";
    private static final String SUB = "-";
    private static final String MUL = "*";

    private List<Token> tokens;

    @BeforeEach
    void setUp() {
        this.tokens = new ArrayList<>();

        AbstractOperator.registerOperator(new Addition(ADD, ADD_P));
        AbstractOperator.registerOperator(new Addition(SUB, SUB_P));
        AbstractOperator.registerOperator(new Addition(MUL, MUL_P));
    }

    @AfterEach
    void tearDown() {
        AbstractOperator.resetRegisteredOperators();
    }

    @Test
    void samePriorities() throws UnexpectedTokenException {
        getTokenBag().add(new Token("1", OPERAND));
        getTokenBag().add(new Token(ADD, OPERATOR));
        getTokenBag().add(new Token("2", OPERAND));
        getTokenBag().add(new Token(SUB, OPERATOR));
        getTokenBag().add(new Token("3", OPERAND));

        final String expected = "12" + ADD + "3" + SUB;
        final String actual = implode(RPN.translate(this.getTokenBag()));

        assertArrayEquals(expected.toCharArray(), actual.toCharArray());
    }

    @Test
    void differentPriorities() throws UnexpectedTokenException {
        getTokenBag().add(new Token("1", OPERAND));
        getTokenBag().add(new Token(ADD, OPERATOR));
        getTokenBag().add(new Token("2", OPERAND));
        getTokenBag().add(new Token(MUL, OPERATOR));
        getTokenBag().add(new Token("3", OPERAND));

        final String expected = "123" + MUL + ADD;
        final String actual = implode(RPN.translate(this.getTokenBag()));

        assertArrayEquals(expected.toCharArray(), actual.toCharArray());
    }

    @Test
    void samePrioritiesWithParenthesis() throws UnexpectedTokenException {
        getTokenBag().add(new Token("1", OPERAND));
        getTokenBag().add(new Token(ADD, OPERATOR));
        getTokenBag().add(new Token("(", OPEN_PARENTHESIS));
        getTokenBag().add(new Token("2", OPERAND));
        getTokenBag().add(new Token(SUB, OPERATOR));
        getTokenBag().add(new Token("3", OPERAND));
        getTokenBag().add(new Token(")", CLOSE_PARENTHESIS));

        final String expected = "123" + SUB + ADD;
        final String actual = implode(RPN.translate(this.getTokenBag()));

        assertArrayEquals(expected.toCharArray(), actual.toCharArray());
    }

    @Test
    void differentPrioritiesWithParenthesis() throws UnexpectedTokenException {
        getTokenBag().add(new Token("1", OPERAND));
        getTokenBag().add(new Token(MUL, OPERATOR));
        getTokenBag().add(new Token("(", OPEN_PARENTHESIS));
        getTokenBag().add(new Token("2", OPERAND));
        getTokenBag().add(new Token(SUB, OPERATOR));
        getTokenBag().add(new Token("3", OPERAND));
        getTokenBag().add(new Token(")", CLOSE_PARENTHESIS));

        final String expected = "123" + SUB + MUL;
        final String actual = implode(RPN.translate(this.getTokenBag()));

        assertArrayEquals(expected.toCharArray(), actual.toCharArray());
    }

    private List<Token> getTokenBag() {
        return this.tokens;
    }

    private static String implode(List<Token> tokens) {
        return String.join("", tokens.stream()
                .map(Token::toString)
                .collect(Collectors.joining(""))
        );
    }
}
