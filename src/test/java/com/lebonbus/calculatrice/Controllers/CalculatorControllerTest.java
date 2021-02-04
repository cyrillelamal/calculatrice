package com.lebonbus.calculatrice.Controllers;

import com.lebonbus.calculatrice.Models.Calculator;
import com.lebonbus.calculatrice.Models.CalculatorModelInterface;
import com.lebonbus.calculatrice.Structurals.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class CalculatorControllerTest {
    private static class TCalculatorController extends CalculatorController {
        public TCalculatorController(CalculatorModelInterface model) {
            super(model);
        }
    }

    private TCalculatorController controller;

    @BeforeEach
    void setUp() {
        this.controller = new TCalculatorController(new Calculator());
    }

    @Test
    void cutOneCharacter() {
        final String input = "123";
        this.input(input);

        final String expected = input.substring(0, input.length() - 1);
        final String actual = this.getController().cut();

        assertArrayEquals(expected.toCharArray(), actual.toCharArray());
    }

    @Test
    void cutEmptyString() {
        final String input = Settings.DEFAULT_TEXT;
        this.input(input);

        final String actual = this.getController().cut();

        assertArrayEquals(input.toCharArray(), actual.toCharArray());
    }

    @Test
    void cutLastCharacter() {
        final String input = "f";
        this.input(input);

        final String expected = Settings.DEFAULT_TEXT;
        final String actual = this.getController().cut();

        assertArrayEquals(expected.toCharArray(), actual.toCharArray());
    }

    @Test
    void addOpenParenthesisAtStart() {
        final String input = Settings.DEFAULT_TEXT;
        this.input(input); // "0"

        final String expected = "(";
        final String actual = this.getController().addParenthesis();

        assertArrayEquals(expected.toCharArray(), actual.toCharArray());
    }

    @Test
    void addOpenParenthesisAfterOperator() {
        final String input = "123+";
        this.input(input); // "123+"

        final String expected = input + TCalculatorController.OPEN_PARENTHESIS; // 123+(
        final String actual = this.getController().addParenthesis();

        assertArrayEquals(expected.toCharArray(), actual.toCharArray());
    }

    @Test
    void addCloseParenthesisAfterOperand() {
        final String input = "123+(456";
        this.input(input); // "123+(456"

        final String expected = input + TCalculatorController.CLOSE_PARENTHESIS; // 123+(
        final String actual = this.getController().addParenthesis();

        assertArrayEquals(expected.toCharArray(), actual.toCharArray());
    }

    @Test
    void addTooManyCloseParentheses() {
        final String input = "123+(456)";
        this.input(input); // "123+(456)"

        final String actual = this.getController().addParenthesis();

        assertArrayEquals(input.toCharArray(), actual.toCharArray());
    }

    @Test
    void appendToNotEmptyString() {
        final String input = "123";
        final String a = "+";

        this.input(input); // 123

        final String expected = input + a; // 123+
        final String actual = this.getController().append(a);

        assertArrayEquals(expected.toCharArray(), actual.toCharArray());
    }

    @Test
    void appendToEmptyString() {
        final String input = Settings.DEFAULT_TEXT;
        final String a = "+";

        this.input(input); // "0"

        final String actual = this.getController().append(a);

        assertArrayEquals(a.toCharArray(), actual.toCharArray());
    }

    private void input(String... input) {
        for (String s : input) this.getController().handleButtonClick(s);
    }

    private TCalculatorController getController() {
        return this.controller;
    }
}
