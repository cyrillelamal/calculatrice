package com.lebonbus.calculatrice.Views;

import com.lebonbus.calculatrice.Controllers.ButtonActionListener;
import com.lebonbus.calculatrice.Controllers.ControllerInterface;
import com.lebonbus.calculatrice.Controllers.InputActionListener;
import com.lebonbus.calculatrice.Models.CalculatorModelInterface;
import com.lebonbus.calculatrice.Structurals.ObserverInterface;
import com.lebonbus.calculatrice.Structurals.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Swing view.
 * The view delegates actions to the controller.
 * The controller updates the model.
 * The updated model notifies the view.
 * ... profit!
 *
 * @see Settings
 */
public class CalculatorView implements ObserverInterface {
    /**
     * The palette
     */
    public static final String[] BUTTONS = {
            "C", "\u2190", "%", "\u00F7",
            "7", "8", "9", "\u00D7",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "( )", "0", ".", "=",
    };
    /**
     * The number of buttons in a row of the palette
     */
    public static final int NB_COLS = 4;

    // MVC
    private final ControllerInterface controller;
    private final CalculatorModelInterface model;

    // The view parts
    private final JFrame frame = new JFrame(Settings.TITLE);
    private final JTextField inout = new JTextField(Settings.DEFAULT_TEXT);

    /**
     * The view delegates actions to its controller and observes a model.
     *
     * @param controller the controller.
     * @param model      the observed model.
     */
    public CalculatorView(final ControllerInterface controller, final CalculatorModelInterface model) {
        this.controller = controller;
        this.model = model;

        this.init();
    }

    /**
     * Make the components visible.
     */
    public void show() {
        this.getFrame().setVisible(true);
    }

    /**
     * @return the current output.
     */
    public String getOutput() {
        return this.getInout().getText();
    }

    /**
     * Update the input text field.
     *
     * @param output the new output.
     * @see ObserverInterface
     */
    @Override
    public void update(final String output) {
        this.getInout().setText(output);
    }

    public ControllerInterface getController() {
        return this.controller;
    }

    public CalculatorModelInterface getModel() {
        return this.model;
    }

    /**
     * Register the view as an observer and prepare it.
     */
    protected void init() {
        // Check if the Swing components have been created already.
        if (!this.getModel().registerObserver(this)) return;

        this.prepareView();
    }

    /**
     * Initialize the components of the view.
     */
    protected void prepareView() {
        this.getFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getFrame().setSize(Settings.WIDTH, Settings.HEIGHT);

        final JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(new EmptyBorder(Settings.CONTAINER_PADDING, Settings.CONTAINER_PADDING, Settings.CONTAINER_PADDING, Settings.CONTAINER_PADDING));
        this.getFrame().add(container);

        this.getInout().setHorizontalAlignment(SwingConstants.RIGHT);
        this.getInout().setFont(Settings.FONT);
        this.getInout().setText(Settings.DEFAULT_TEXT);
        this.getInout().addActionListener(new InputActionListener(this.getController()));
        container.add(this.getInout());

        final JPanel palette = new JPanel();
        palette.setLayout(new GridLayout(0, CalculatorView.NB_COLS));
        container.add(palette);

        for (final String b : CalculatorView.BUTTONS) {
            final JButton btn = new JButton(b);
            btn.setMargin(new Insets(Settings.BUTTON_MARGIN, Settings.BUTTON_MARGIN, Settings.BUTTON_MARGIN, Settings.BUTTON_MARGIN));
            btn.addActionListener(new ButtonActionListener(b, this.getController()));
            palette.add(btn);
        }
    }

    private JFrame getFrame() {
        return this.frame;
    }

    private JTextField getInout() {
        return this.inout;
    }
}
