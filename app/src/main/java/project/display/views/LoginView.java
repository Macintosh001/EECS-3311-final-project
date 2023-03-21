package project.display.views;

import project.display.Display;
import project.display.views.builders.OtherBuilder;
import project.logic.StockCheckingLogic;

import javax.swing.*;

public class LoginView extends JPanel {

    OtherBuilder builder = new OtherBuilder();
    /**
     * @param display represents the JFrame that contains this panel
     */
    public LoginView(Display display) {

        // Setup the Panel
        super();
        setBounds(0, 0, 800, 600);
        setLayout(null);

        // Add all the UI components
        JButton loginAsEmployee = builder.buildButton("Login as Employee (Client)");
        loginAsEmployee.setBounds(110, 210, 380, 80);
        loginAsEmployee.addActionListener(e -> {
            display.advanceTo(display.getEmployeeView());
        });
        add(loginAsEmployee);

        JButton loginAsManager = builder.buildButton("Login as Manager (Client)");
        loginAsManager.setBounds(110, 310, 380, 80);
        loginAsManager.addActionListener(e -> {
            display.advanceTo(display.getManagerView());
        });
        add(loginAsManager);

        JButton loginAsAdmin = builder.buildButton("Login as Admin (Dev/QA/TA)");
        loginAsAdmin.setBounds(110, 410, 380, 80);
        loginAsAdmin.addActionListener(e -> {
            display.advanceTo(display.getAdminView());
        });
        add(loginAsAdmin);

        // Hide when initialized
        setVisible(false);

    }
}
