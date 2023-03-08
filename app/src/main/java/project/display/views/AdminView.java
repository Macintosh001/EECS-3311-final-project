package project.display.views;

import project.display.Display;
import project.display.buttons.BackButton;

import javax.swing.*;

public class AdminView extends JPanel {

    public AdminView(Display display) {

        // Setup the Panel
        super();
        setBounds(0, 0, 1000, 700);
        setLayout(null);

        // Add the UI elements
        JButton scButton = new JButton("Check what's in Stock");
        scButton.setBounds(110, 210, 180, 80);
        scButton.addActionListener(e -> {
            display.advanceTo(display.getStockCheckingView());
        });
        add(scButton);

        BackButton logoutButton = new BackButton("Logout", display);
        add(logoutButton);

        // Hide when initialized
        setVisible(false);

    }
}
