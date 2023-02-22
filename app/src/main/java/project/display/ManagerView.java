package project.display;

import project.logic.ILogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
/*
This view pertains to all the controls and data a manager should have access too
general view of the inventory in a table format
buttons representing operations that the manager may want to use
 */
public class ManagerView extends MainDisplay{

    public ManagerView(ILogic logic){

        JFrame frame = new JFrame("Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,250);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        JButton couponButton = new JButton("Coupons");
        couponButton.setBounds(200, 10, 200, 50);
        couponButton.addActionListener(e -> new AddDialog(this));
        frame.add(couponButton);

        JButton stockButton = new JButton("Stock");
        stockButton.setBounds(200, 70, 200, 50);
        stockButton.addActionListener(e -> new RemoveDialog(this));
        frame.add(stockButton);

        JButton orderButton = new JButton("Order");
        orderButton.setBounds(200, 130, 200, 50);
        orderButton.addActionListener(e -> new RemoveDialog(this));
        frame.add(orderButton);

        frame.setVisible(true);
    }
}
