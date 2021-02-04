package com.lebonbus.calculatrice.Models;

import com.lebonbus.calculatrice.Exceptions.UnexpectedTokenException;
import com.lebonbus.calculatrice.Models.Operators.AbstractOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * RPN utilities.
 */
public class RPN {
    /**
     * Map the list of tokens in the infix notation to the list of tokens in the postfix (RPN) notation.
     *
     * @param infix the list of tokens in the infix notation.
     * @return the list of tokens in the postfix notation.
     * @throws UnexpectedTokenException thrown when the token is not recognized as a valid token.
     */
    public static List<Token> translate(final List<Token> infix) throws UnexpectedTokenException {
        final List<Token> postfix = new ArrayList<>(); // postfix
        final Stack<Token> stack = new Stack<>();

        for (final Token token : infix) {
            switch (token.getRole()) {
                case OPERAND -> postfix.add(token);
                case OPERATOR -> RPN.treatOperator(postfix, stack, token);
                case OPEN_PARENTHESIS -> stack.push(token);
                case CLOSE_PARENTHESIS -> RPN.treatCloseParenthesis(postfix, stack);
            }
        }
        while (!stack.isEmpty()) postfix.add(stack.pop());

        return postfix;
    }

    /**
     * Operate the stack and the output depending on the priorities of the operators.
     *
     * @param postfix the current list of tokens in the postfix notation.
     * @param stack   the current stack of tokens formed while translation.
     * @param opThis  the current operator.
     * @throws UnexpectedTokenException thrown when the token is not recognized as a valid token.
     */
    private static void treatOperator(final List<Token> postfix, final Stack<Token> stack, final Token opThis) throws UnexpectedTokenException {
        if (!stack.isEmpty()) {
            final Token opTop = stack.pop();

            if (opTop.getRole() == Token.Role.OPEN_PARENTHESIS) stack.push(opTop);
            else if (AbstractOperator.getByToken(opTop).compareTo(AbstractOperator.getByToken(opThis)) < 0)
                stack.push(opTop);
            else postfix.add(opTop);
        }

        stack.push(opThis);
    }

    /**
     * Operate the stack and the output depending on the order of tokens in the expression.
     *
     * @param postfix the current list of tokens in the postfix notation.
     * @param stack   the current stack of tokens formed while translation.
     */
    private static void treatCloseParenthesis(final List<Token> postfix, final Stack<Token> stack) {
        while (!stack.isEmpty()) {
            final Token el = stack.pop();

            if (el.getRole() == Token.Role.OPEN_PARENTHESIS) break;
            else postfix.add(el);
        }
    }
}
