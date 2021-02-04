package com.lebonbus.calculatrice.Controllers;

public interface ControllerInterface extends Runnable {
    /**
     * Handle any button clicks.
     *
     * @param key the button text or any other key that enables to determine the further action.
     */
    void handleButtonClick(String key);
}
