package com.lebonbus.calculatrice.Controllers;

import com.lebonbus.calculatrice.Exceptions.UnexpectedTokenException;
import com.lebonbus.calculatrice.Models.CalculatorModelInterface;
import com.lebonbus.calculatrice.Models.Operators.*;
import com.lebonbus.calculatrice.Models.Token;
import com.lebonbus.calculatrice.Structurals.Settings;
import com.lebonbus.calculatrice.Views.CalculatorView;

import java.util.ArrayList;
import java.util.List;

public class CalculatorController implements ControllerInterface {
    public static final char OPEN_PARENTHESIS = '(';
    public static final char CLOSE_PARENTHESIS = ')';

    private final CalculatorModelInterface model;
    private final CalculatorView view;

    /**
     * Start with a new model and initialize the view for it.
     *
     * @param model the initial model.
     */
    public CalculatorController(final CalculatorModelInterface model) {
        this.model = model;
        this.view = new CalculatorView(this, this.getModel());
    }

    /**
     * Build the view and show it.
     * Normally, the method should be called only once.
     * But the situations when you have to run several controllers with several views is also possible.
     */
    public void run() {
        CalculatorController.registerOperators();
        this.getView().show();
    }

    /**
     * Perform the action listener's action.
     * <p>
     * Map the button value to the new output.
     *
     * @param key the button key.
     */
    public void handleButtonClick(final String key) {
        this.getModel().setOutput(switch (key) {
            case "C" -> Settings.DEFAULT_TEXT;
            case "\u2190" -> this.cut();
            case "=" -> this.evaluate();
            case "( )" -> this.addParenthesis();
            default -> this.append(key);
        });
    }

    /**
     * Register all available operators.
     */
    protected static void registerOperators() {
        // Constructors without parameters use the corresponding constants.
        AbstractOperator.registerOperator(new Addition());
        AbstractOperator.registerOperator(new Subtraction());
        AbstractOperator.registerOperator(new Multiplication("*", Multiplication.PRIORITY));
        AbstractOperator.registerOperator(new Multiplication());
        AbstractOperator.registerOperator(new Division());
        AbstractOperator.registerOperator(new Division("/", Division.PRIORITY));
        AbstractOperator.registerOperator(new Percentage());
    }

    /**
     * Remove the last character.
     * <p>
     * If the resulting string is too short, the default empty string is returned.
     *
     * @return the expression without the last character.
     * @see Settings#DEFAULT_TEXT
     */
    protected String cut() {
        String e = this.getView().getOutput().trim();

        if (e.equals(String.valueOf(Double.NaN))) e = Settings.DEFAULT_TEXT;
        if (e.length() > 0) e = e.substring(0, e.length() - 1);
        if (e.length() == 0) e = Settings.DEFAULT_TEXT;

        return e;
    }

    /**
     * Parse tokens and delegate the evaluation to the model.
     * <p>
     * If the parsed tokens form an incorrect expression, the Double.NaN is returned.
     *
     * @return the result of evaluation.
     */
    protected String evaluate() {
        try {
            this.getModel().setTokens(this.parseTokens());
            return this.getModel().evaluate().toString();
        } catch (UnexpectedTokenException ignored) {
            return Double.toString(Double.NaN);
        }
    }

    /**
     * Add an opening or closing parenthesis to the expression.
     *
     * @return the new expression.
     */
    protected String addParenthesis() {
        final String e = this.getView().getOutput();

        // The very beginning, e.g. empty string
        if (this.hasDefaultValue())
            return String.valueOf(CalculatorController.OPEN_PARENTHESIS);

        final char lc = e.charAt(e.length() - 1);
        long nbOpen = 0, nbClose = 0;
        for (final char ch : e.toCharArray()) {
            if (ch == CalculatorController.OPEN_PARENTHESIS) nbOpen++;
            else if (ch == CalculatorController.CLOSE_PARENTHESIS) nbClose++;
        }

        if (lc >= 0x30 && lc < 0x3A || lc == CalculatorController.CLOSE_PARENTHESIS) {
            if (nbOpen > nbClose) return e + CalculatorController.CLOSE_PARENTHESIS;
            else if (nbOpen == nbClose) return e;
        }
        if (lc != CalculatorController.CLOSE_PARENTHESIS && lc != 0x2E)
            return e + CalculatorController.OPEN_PARENTHESIS;

        return e;
    }

    /**
     * Append a token to the end of the expression.
     *
     * @param a the token.
     * @return the new expression.
     */
    protected String append(final String a) {
        final String e = this.getView().getOutput();

        return this.hasDefaultValue() ? a : e + a;
    }

    protected boolean hasDefaultValue() {
        final String e = this.getView().getOutput().replaceAll("\\s", "");

        return e.equals(Settings.DEFAULT_TEXT) || e.length() == 0 || e.equals(String.valueOf(Double.NaN));
    }

    /**
     * Parse the string to a list of tokens.
     *
     * @return the list of correct tokens.
     */
    protected List<Token> parseTokens() throws UnexpectedTokenException {
        final List<Token> tokens = new ArrayList<>();

        StringBuffer operand = new StringBuffer();
        for (final char ch : this.getView().getOutput().replaceAll("\\s", "").toCharArray()) {
            if (ch >= 0x30 && ch < 0x3A || ch == 0x2E) {
                operand.append(ch);
                continue;
            }

            if (!operand.isEmpty()) {
                tokens.add(new Token(operand.toString(), Token.Role.OPERAND));
                operand = new StringBuffer();
            }

            if (ch == CalculatorController.OPEN_PARENTHESIS) {
                tokens.add(new Token(String.valueOf(ch), Token.Role.OPEN_PARENTHESIS));
            } else if (ch == CalculatorController.CLOSE_PARENTHESIS) {
                tokens.add(new Token(String.valueOf(ch), Token.Role.CLOSE_PARENTHESIS));
            } else {
                final Token token = new Token(String.valueOf(ch), Token.Role.OPERATOR);
                if (AbstractOperator.canEvaluateToken(token)) tokens.add(token);
                else throw new UnexpectedTokenException(token);
            }
        }
        // The very last operand
        if (!operand.isEmpty()) tokens.add(new Token(operand.toString(), Token.Role.OPERAND));

        return tokens;
    }

    private CalculatorModelInterface getModel() {
        return this.model;
    }

    private CalculatorView getView() {
        return this.view;
    }
}
