package project.display;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialDisplay {

    private JFrame frame;
    private final String user = "user";
    private final String passw = "pass123";

    public InitialDisplay(){


        // instantiate the window, fix the size ect.
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
                // need to handle username and password info here
                String p = new String(password.getPassword());
                String u = usernameInput.getText();

//                if(!(p.equals("pass123")&& u.equals("user"))){
//                    JFrame f = new JFrame();
//                    JOptionPane.showMessageDialog(f,"Incorrect Username and Password","Alert",JOptionPane.WARNING_MESSAGE);
//                }
//                if(managerButton.isSelected() || employeeButton.isSelected()) {
//                    MainDisplay mainDisplay = new MainDisplay(logic);
//                    mainDisplay.regenTable();
//                }
            }
        });
    }

}
