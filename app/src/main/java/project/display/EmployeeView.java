package project.display;

import project.logic.ILogic;

import javax.swing.*;

/*
employee view should show the inventory in a table format
only able to search the table for specific items
 */
public class EmployeeView extends MainDisplay{

    public EmployeeView(ILogic logic){

        JFrame frame = new JFrame("Employee");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        JButton stockCheckButton = new JButton("Stock");
        stockCheckButton.setBounds(200, 10, 200, 50);
        stockCheckButton.addActionListener(e -> new AddDialog(this));
        frame.add(stockCheckButton);

        JButton saleButton = new JButton("Sale");
        saleButton.setBounds(200, 70, 200, 50);
        saleButton.addActionListener(e -> new RemoveDialog(this));
        frame.add(saleButton);

        frame.setVisible(true);
    }
}
