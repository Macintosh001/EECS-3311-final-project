package project.display.views;

import project.display.Display;
import project.display.views.builders.OtherBuilder;

import javax.print.attribute.standard.MediaSize;
import javax.swing.*;

public class ManagerView extends JPanel {

    private OtherBuilder builder = new OtherBuilder();
    /**
     * @param display represents the JFrame that contains this panel
     */
    public ManagerView(Display display) {

        // Setup the Panel
        super();
        setBounds(0, 0, 1000, 700);
        setLayout(null);

        JButton smButton = builder.buildButton("Manage what's in Stock");
        smButton.setBounds(110, 210, 180, 80);
        smButton.addActionListener(e -> {
            display.advanceTo(display.getStockManagingView());
        });
        add(smButton);

        JButton oButton = builder.buildButton("Order more Stock");
        oButton.setBounds(110, 310, 180, 80);
        oButton.addActionListener(e -> {
            display.advanceTo(display.getOrderView());
        });
        add(oButton);


        JButton cpButton = builder.buildButton("Manage Coupons");
        cpButton.setBounds(110, 410, 180, 80);
        cpButton.addActionListener(e -> {
            display.advanceTo(display.getCouponManagerView());
        });
        add(cpButton);

        JButton logoutButton = builder.buildButton("Logout");
        logoutButton.setBounds(810, 610, 180, 80);
        logoutButton.addActionListener(e -> {
            display.back();
        });
        add(logoutButton);

        JButton modButton = builder.buildButton("Manage Modifiers(tentative)");
        modButton.setBounds(110, 510, 180, 80);
        modButton.addActionListener(e -> {
            display.advanceTo(display.getModView());
        });
        add(modButton);

        JButton aButton = builder.buildButton("Automation");
        aButton.setBounds(310, 210, 180, 80);
        aButton.addActionListener(e -> {
            display.advanceTo(display.getAutoView());
        });
        add(aButton);

        // Hide when initialized
        setVisible(false);
    }
}
