package com.lebonbus.calculatrice.Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputActionListener implements ActionListener {
    public static final String KEY = "=";

    private final ControllerInterface controller;

    /**
     * An entry point to the controller.
     *
     * @param controller the controller that the action is delegated to.
     */
    public InputActionListener(final ControllerInterface controller) {
        this.controller = controller;
    }

    /**
     * Imitates the button click.
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        new ButtonActionListener(InputActionListener.KEY, this.getController()).actionPerformed(e);
    }

    private ControllerInterface getController() {
        return this.controller;
    }
}
