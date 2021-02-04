package com.lebonbus.calculatrice.Models;

import com.lebonbus.calculatrice.Exceptions.UnexpectedTokenException;
import com.lebonbus.calculatrice.Structurals.SubjectInterface;

import java.util.List;

public interface CalculatorModelInterface extends SubjectInterface {
    /**
     * Evaluate the current expression.
     * The result can be any Number - it depends on the token set.
     *
     * @return the result of evaluation.
     */
    Number evaluate() throws UnexpectedTokenException;

    /**
     * Renew the list of tokens.
     *
     * @param tokens the new token set.
     */
    void setTokens(List<Token> tokens);

    /**
     * @return the current token set.
     */
    List<Token> getTokens();

    void setOutput(String output);

    String getOutput();
}
