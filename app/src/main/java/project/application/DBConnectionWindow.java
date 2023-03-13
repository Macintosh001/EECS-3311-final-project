package project.application;

import javax.swing.*;
import java.sql.SQLException;

public class DBConnectionWindow {
    public DBConnectionWindow () {
        // This JFrame is just to let the user enter their database username and password
        JFrame f = new JFrame("Enter your Database Password!!!");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 600);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setResizable(false);

        JLabel userLabel = new JLabel("Username (probably 'root'):");
        userLabel.setBounds(100, 200, 200, 50);
        f.add(userLabel);

        JTextField userInput = new JTextField();
        userInput.setBounds(310, 200, 200, 50);
        f.add(userInput);

        JLabel passLabel = new JLabel("Database Password:");
        passLabel.setBounds(100, 270, 200, 50);
        f.add(passLabel);

        JPasswordField passInput = new JPasswordField();
        passInput.setBounds(310, 270, 200, 50);
        f.add(passInput);

        JButton connectButton = new JButton("Login and Connect to DB");
        connectButton.setBounds(100, 340, 250, 50);
        connectButton.addActionListener(e -> {
            try {
                App.initWithSQL(userInput.getText(), new String(passInput.getPassword()));
                f.dispose();
            } catch (SQLException ex) {
                JDialog fail = new JDialog();
                fail.setTitle("Error!");
                fail.setSize(400, 100);
                fail.setLocationRelativeTo(null);
                fail.setLayout(null);
                fail.setResizable(false);

                JLabel label1 = new JLabel("Problem connecting to database!");
                label1.setBounds(50, 10, 300, 20);
                fail.add(label1);

                JLabel label2 = new JLabel("Make sure username and password are correct.");
                label2.setBounds(50, 40, 300, 20);
                fail.add(label2);

                fail.setVisible(true);
            }
        });
        f.add(connectButton);

        JButton stubButton = new JButton("Use Stub Instead of SQL");
        stubButton.setBounds(400, 340, 250, 50);
        stubButton.addActionListener(e -> {
            App.initWithStub();
        });
        f.add(stubButton);

        f.setVisible(true);
    }
}
