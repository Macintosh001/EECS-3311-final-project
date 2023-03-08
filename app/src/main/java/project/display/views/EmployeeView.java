package project.display.views;

import project.display.Display;

import javax.swing.*;

public class EmployeeView extends JPanel {

    public EmployeeView(Display display) {

        // Setup the Panel
        super();
        setBounds(0, 0, 1000, 700);
        setLayout(null);

        JLabel label = new JLabel("Employee View");
        label.setBounds(0, 0, 200, 30);
        add(label);

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
