package com.lebonbus.calculatrice.Structurals;

import java.util.ArrayList;
import java.util.List;

public interface SubjectInterface {
    List<ObserverInterface> observers = new ArrayList<>();

    /**
     * Register an observer.
     *
     * @param o the observer.
     * @return true, if observer has been added.
     */
    default boolean registerObserver(ObserverInterface o) {
        return this.getObservers().add(o);
    }

    /**
     * Update the registered observers.
     */
    void updateObservers();

    /**
     * @return the list of registered observers.
     */
    default List<ObserverInterface> getObservers() {
        return this.observers;
    }
}
