package com.lebonbus.calculatrice.Exceptions;

import com.lebonbus.calculatrice.Models.Token;

/**
 * Should be thrown when the token has an inappropriate place in the expression.
 */
public class UnexpectedTokenException extends Exception {
    private final Token token;

    /**
     * Thrown when the token is unexpected.
     *
     * @param token the unexpected token.
     */
    public UnexpectedTokenException(final Token token) {
        super(String.format("Unexpected token \"%s\".", token.toString()));

        this.token = token;
    }

    /**
     * Returns the unexpected token that invokes the exception.
     *
     * @return the unexpected token.
     */
    public Token getToken() {
        return this.token;
    }
}
