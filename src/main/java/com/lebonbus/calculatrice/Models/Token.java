package com.lebonbus.calculatrice.Models;

public class Token {
    private final String key;
    private final Role role;

    /**
     * Available roles.
     */
    public enum Role {
        OPERAND,
        OPEN_PARENTHESIS,
        CLOSE_PARENTHESIS,
        OPERATOR,
    }

    /**
     * Create a new raw token.
     *
     * @param key  the string representation of the token.
     * @param role the role of the token in the expression.
     */
    public Token(final String key, final Role role) {
        this.key = key;
        this.role = role;
    }

    /**
     * @return the string representation of the token.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * @return the role of the token in the expression.
     */
    public Role getRole() {
        return this.role;
    }

    /**
     * Parse the token to Number.
     *
     * @return Long or Double value. If the token is not a number, the method returns Double.Nan.
     */
    public Number toNumber() {
        try {
            return Long.parseLong(this.getKey());
        } catch (NumberFormatException ignored) {
        }
        try {
            return Double.parseDouble(this.getKey());
        } catch (NumberFormatException ignored) {
        }

        return Double.NaN;
    }

    @Override
    public String toString() {
        return this.getKey();
    }
}
