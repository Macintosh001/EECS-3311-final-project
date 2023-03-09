package project.display.views;

import project.display.Display;

import javax.swing.*;

public class EmployeeView extends JPanel {

    /**
     * @param display represents the JFrame that contains this panel
     */
    public EmployeeView(Display display) {

        // Setup the Panel
        super();
        setBounds(0, 0, 1000, 700);
        setLayout(null);

        JButton scButton = new JButton("Check what's in Stock");
        scButton.setBounds(110, 210, 180, 80);
        scButton.addActionListener(e -> {
            display.advanceTo(display.getStockCheckingView());
        });
        add(scButton);

        JButton sButton = new JButton("Point of Sale");
        sButton.setBounds(110, 310, 180, 80);
        sButton.addActionListener(e -> {
            display.advanceTo(display.getSaleView());
        });
        add(sButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(810, 610, 180, 80);
        logoutButton.addActionListener(e -> {
            display.back();
        });
        add(logoutButton);

        // Hide when initialized
        setVisible(false);

    }
}
