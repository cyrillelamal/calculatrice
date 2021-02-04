package com.lebonbus.calculatrice.Models;

import com.lebonbus.calculatrice.Exceptions.UnexpectedTokenException;
import com.lebonbus.calculatrice.Models.Operators.AbstractOperator;
import com.lebonbus.calculatrice.Structurals.ObserverInterface;
import com.lebonbus.calculatrice.Structurals.SubjectInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Calculator implements CalculatorModelInterface, SubjectInterface {
    private String output = ""; // infix
    private List<Token> tokens; // infix

    /**
     * Create a new calculator model with a starter set of tokens.
     *
     * @param tokens a set of tokens that form an expression.
     */
    public Calculator(final List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * Create a new calculator model without any starter set of tokens.
     */
    public Calculator() {
        this(new ArrayList<>());
    }

    /**
     * Evaluate the tokens as an expression.
     * <p>
     * This implementation translates the set of infix tokens to the set of postfix tokens.
     * Then, and only then, it evaluates the RPN presentation without parenthesis.
     *
     * @return the result of the evaluated expression.
     * @throws UnexpectedTokenException thrown when the parsed expression is incorrect.
     * @see CalculatorModelInterface#evaluate()
     */
    @Override
    public Number evaluate() throws UnexpectedTokenException {
        final Stack<Number> stack = new Stack<>();
        stack.push(0L); // The default value

        for (final Token token : RPN.translate(this.getTokens())) {
            if (token.getRole() == Token.Role.OPERATOR) {
                final Number right = stack.pop();
                final Number left = stack.pop();
                stack.push(AbstractOperator.getByToken(token).apply(left, right));
            } else if (token.getRole() == Token.Role.OPERAND) stack.push(token.toNumber());
            else throw new UnexpectedTokenException(token);
        }

        return stack.pop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTokens(final List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Token> getTokens() {
        return this.tokens;
    }

    /**
     * Update the output.
     */
    @Override
    public void updateObservers() {
        for (ObserverInterface o : this.getObservers()) o.update(this.getOutput());
    }

    public String getOutput() {
        return this.output;
    }

    public void setOutput(final String output) {
        this.output = output;

        this.updateObservers();
    }
}
