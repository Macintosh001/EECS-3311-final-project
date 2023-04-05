package project.display.views;

import project.display.Display;
import project.display.views.builders.OtherBuilder;

import javax.swing.*;

public class EmployeeView extends JPanel {

    private OtherBuilder builder = new OtherBuilder();
    /**
     * @param display represents the JFrame that contains this panel
     */
    public EmployeeView(Display display) {

        // Setup the Panel
        super();
        setBounds(0, 0, 1000, 700);
        setLayout(null);

        JButton scButton = builder.buildButton("Check what's in Stock");
        scButton.setBounds(110, 210, 180, 80);
        scButton.addActionListener(e -> {
            display.advanceTo(display.getStockCheckingView());
        });
        add(scButton);

        JButton sButton = builder.buildButton("Point of Sale");
        sButton.setBounds(110, 310, 180, 80);
        sButton.addActionListener(e -> {
            display.advanceTo(display.getSaleView());
        });
        add(sButton);

        JButton logoutButton = builder.buildButton("Logout");
        logoutButton.setBounds(810, 610, 180, 80);
        logoutButton.addActionListener(e -> {
            display.back();
        });
        add(logoutButton);

        // Hide when initialized
        setVisible(false);

    }
}
