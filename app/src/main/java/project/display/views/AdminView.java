package project.display.views;

import net.miginfocom.swing.MigLayout;
import project.display.Display;
import project.display.buttons.BackButton;
import project.display.views.builders.OtherBuilder;

import javax.swing.*;

public class AdminView extends JPanel {

    private OtherBuilder builder = new OtherBuilder();

    /**
     * @param display represents the JFrame that contains this panel
     */
    public AdminView(Display display) {

        // Setup the Panel
        super();
        setBounds(0, 0, 1000, 700);
        setLayout(null);
        // Add the UI elements
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

        JButton smButton = builder.buildButton("Manage what's in Stock");
        smButton.setBounds(310, 210, 180, 80);
        smButton.addActionListener(e -> {
            display.advanceTo(display.getStockManagingView());
        });
        add(smButton);

        JButton oButton = builder.buildButton("Order more Stock");
        oButton.setBounds(310, 310, 180, 80);
        oButton.addActionListener(e -> {
            display.advanceTo(display.getOrderView());
        });
        add(oButton);


        JButton cpButton = builder.buildButton("Manage Coupons");
        cpButton.setBounds(310, 410, 180, 80);
        cpButton.addActionListener(e -> {
            display.advanceTo(display.getCouponManagerView());
        });
        add(cpButton);

        JButton aButton = builder.buildButton("Automation");
        aButton.setBounds(110, 410, 180, 80);
        aButton.addActionListener(e -> {
            display.advanceTo(display.getAutoView());
        });
        add(aButton);

        BackButton logoutButton = new BackButton("Logout", display);
        add(logoutButton);

        // Hide when initialized
        setVisible(false);

    }
}
