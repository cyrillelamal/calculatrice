package com.lebonbus.calculatrice.Structurals;

/**
 * The Observer pattern carcass.
 */
public interface ObserverInterface {
    /**
     * Notify about changes in the subject.
     */
    void update(String inout);
}
