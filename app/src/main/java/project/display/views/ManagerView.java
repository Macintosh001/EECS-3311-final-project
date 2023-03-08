package project.display.views;

import project.display.Display;

import javax.swing.*;

public class ManagerView extends JPanel {
    public ManagerView(Display display) {

        // Setup the Panel
        super();
        setBounds(0, 0, 1000, 700);
        setLayout(null);

        JButton smButton = new JButton("Manage what's in Stock");
        smButton.setBounds(110, 210, 180, 80);
        smButton.addActionListener(e -> {
            display.advanceTo(display.getStockManagingView());
        });
        add(smButton);

        JButton oButton = new JButton("Order more Stock");
        oButton.setBounds(110, 310, 180, 80);
        oButton.addActionListener(e -> {
            display.advanceTo(display.getOrderView());
        });
        add(oButton);


        JButton cpButton = new JButton("Manage Coupons");
        cpButton.setBounds(110, 410, 180, 80);
        cpButton.addActionListener(e -> {
            display.advanceTo(display.getCouponManagerView());
        });
        add(cpButton);

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
