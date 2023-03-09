package project.display.buttons;

import project.display.Display;

import javax.swing.*;

public class BackButton extends JButton {
    /**
     * @param label pass in the label for the button
     * @param display pass in the JFrame that contains the button
     */
    public BackButton(String label, Display display) {
        super(label);
        setBounds(810, 610, 180, 80);
        addActionListener(e -> {
            display.back();
        });
    }
}
