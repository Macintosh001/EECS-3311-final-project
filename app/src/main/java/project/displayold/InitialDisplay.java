package project.displayold;


import project.logic.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialDisplay {

    private StockCheckingLogic scLogic = null;
    private SaleLogic sLogic = null;
    public CouponManagerLogic cLogic = null;
    private StockManagingLogic smLogic = null;
    private OrderLogic oLogic = null;

    public StockCheckingLogic getScLogic() {
        return scLogic;
    }

    public SaleLogic getsLogic() {
        return sLogic;
    }

    public CouponManagerLogic getcLogic() {
        return cLogic;
    }

    public StockManagingLogic getSmLogic() {
        return smLogic;
    }

    public OrderLogic getoLogic() {
        return oLogic;
    }

    private final String user = "user";
    private final String passw = "pass123";

    public InitialDisplay(StockCheckingLogic scLogic, SaleLogic sLogic,
                          CouponManagerLogic cLogic, StockManagingLogic smLogic, OrderLogic oLogic){


        // instantiate the window, fix the size ect.
        JFrame frame = new JFrame("Landing Page");
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
        JRadioButton devButton = new JRadioButton("Dev/QA");
        devButton.setBounds(190,120,120,30);

        ButtonGroup group = new ButtonGroup();
        group.add(managerButton);
        group.add(employeeButton);
        group.add(devButton);

        JButton login = new JButton("Login");
        login.setBounds(100,175, 80,30);

        frame.add(username);
        frame.add(pass);
        frame.add(password);
        frame.add(login);
        frame.add(usernameInput);
        frame.add(managerButton);
        frame.add(employeeButton);
        frame.add(devButton);
        frame.setVisible(true);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // need to handle username and password info here
                String p = new String(password.getPassword());
                String u = usernameInput.getText();

                if(!(p.equals("pass123")&& u.equals("user"))){
                    JFrame f = new JFrame();
                    JOptionPane.showMessageDialog(f,"Incorrect Username and Password","Alert",JOptionPane.WARNING_MESSAGE);
                }
                else if(managerButton.isSelected()) {
                    ManagerView mView = new ManagerView(scLogic,sLogic,cLogic,smLogic,oLogic);
                    frame.dispose();
                } else if (employeeButton.isSelected()) {
                    EmployeeView eView = new EmployeeView(scLogic,sLogic,cLogic,smLogic,oLogic);
                    frame.dispose();
                } else if (devButton.isSelected()) {
                    DevView dView = new DevView(cLogic,smLogic,oLogic,scLogic,sLogic);
                    frame.dispose();
                }
            }
        });
    }
}
