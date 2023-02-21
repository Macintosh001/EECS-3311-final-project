package project.display;

import project.logic.ILogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialDisplay {

    private JFrame frame;
    private ILogic logic;

    public InitialDisplay(){

        this.logic = logic;
        // instantiate the window, fix the size ect.
        DefaultTableModel tableModel = new DefaultTableModel();
        frame = new JFrame("Landing Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,275);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        // create components to go inside the frame
        JLabel username = new JLabel("username");
        username.setBounds(20,20,80, 30);

        JTextField usernameInput = new JTextField();
        usernameInput.setBounds(100,20,100,30);

        JLabel pass = new JLabel("password");
        pass.setBounds(20,75,80, 30);

        JPasswordField password = new JPasswordField();
        password.setBounds(100,75,100,30);

        JRadioButton managerButton = new JRadioButton("Manager");
        managerButton.setBounds(20,120,120,30);
        JRadioButton employeeButton = new JRadioButton("Employee");
        employeeButton.setBounds(100,120,120,30);

        JButton login = new JButton("Login");
        login.setBounds(100,175, 80,30);

        frame.add(username);
        frame.add(pass);
        frame.add(password);
        frame.add(login);
        frame.add(usernameInput);
        frame.add(managerButton);
        frame.add(employeeButton);
        frame.setVisible(true);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(managerButton.isSelected()){
                    MainDisplay mainDisplay = new MainDisplay(logic);
                } else {
                    MainDisplay mainDisplay = new MainDisplay(logic);
                }
            }
        });
    }

}
