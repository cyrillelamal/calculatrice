package com.lebonbus.calculatrice.Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonActionListener implements ActionListener {
    private final String key;
    private final ControllerInterface controller;

    /**
     * An entry point to the controller.
     *
     * @param key        the identifier of the clicked button.
     * @param controller the controller that the action is delegated to.
     */
    public ButtonActionListener(final String key, final ControllerInterface controller) {
        this.key = key;
        this.controller = controller;
    }

    /**
     * Delegate the action to the controller.
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        this.getController().handleButtonClick(this.getKey());
    }

    private String getKey() {
        return this.key;
    }

    private ControllerInterface getController() {
        return this.controller;
    }
}
