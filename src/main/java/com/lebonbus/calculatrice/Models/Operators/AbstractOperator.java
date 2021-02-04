package com.lebonbus.calculatrice.Models.Operators;

import com.lebonbus.calculatrice.Exceptions.UnexpectedTokenException;
import com.lebonbus.calculatrice.Models.Token;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

/**
 * The container of supported operators.
 */
public abstract class AbstractOperator implements BinaryOperator<Number>, Comparable<AbstractOperator> {
    private static final Map<String, AbstractOperator> operators = new HashMap<>();

    private final String key;
    private final int priority;

    /**
     * Create a new functor.
     *
     * @param priority determines the order of execution in the expression.
     */
    public AbstractOperator(final String key, final int priority) {
        this.key = key;
        this.priority = priority;
    }

    /**
     * The token mapping.
     *
     * @return the token representation of the operator.
     */
    public String getKey() {
        return key;
    }

    /**
     * The bigger priority forces the functor to be executed first.
     *
     * @return the priority of the operator.
     */
    public int getPriority() {
        return this.priority;
    }

    @Override
    public int compareTo(AbstractOperator o) {
        return this.getPriority() - o.getPriority();
    }

    /**
     * Map a raw token to the executable operator.
     *
     * @param t the raw token.
     * @return the corresponding operator.
     * @throws UnexpectedTokenException thrown when the token is not recognized as an operator.
     */
    public static AbstractOperator getByToken(final Token t) throws UnexpectedTokenException {
        if (!AbstractOperator.canEvaluateToken(t)) throw new UnexpectedTokenException(t);

        return AbstractOperator.getOperators().get(t.getKey());
    }

    /**
     * Check if the token can be evaluated.
     *
     * @param t the checked token.
     * @return true, if the token is registered and supported.
     */
    public static boolean canEvaluateToken(final Token t) {
        return AbstractOperator.getOperators().containsKey(t.getKey());
    }

    /**
     * The singleton container of operators for the application.
     *
     * @return the registered operators.
     */
    public static Map<String, AbstractOperator> getOperators() {
        return AbstractOperator.operators;
    }

    /**
     * Register an operator in the list of supported operators.
     *
     * @param o the registered operator.
     */
    public static void registerOperator(final AbstractOperator o) {
        AbstractOperator.getOperators().put(o.getKey(), o);
    }

    /**
     * Remove all registered operators.
     */
    public static void resetRegisteredOperators() {
        AbstractOperator.getOperators().clear();
    }
}
