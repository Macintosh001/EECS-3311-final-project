package project.display;

import project.display.dialog.AddDialog;
import project.logic.SaleLogic;
import project.logic.StockCheckingLogic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
employee view should show the inventory in a table format
only able to search the table for specific items
 */
public class EmployeeView extends MainDisplay{

    private StockCheckingLogic scLogic = null;
    private SaleLogic sLogic = null;
    public EmployeeView(StockCheckingLogic scLogic, SaleLogic sLogic){

        JFrame frame = new JFrame("Employee");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        JButton stockCheckButton = new JButton("Stock");
        stockCheckButton.setBounds(200, 10, 200, 50);
        stockCheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockCheckingView scView = new StockCheckingView(scLogic);
            }
        });
        frame.add(stockCheckButton);

        JButton saleButton = new JButton("Sale");
        saleButton.setBounds(200, 70, 200, 50);
        saleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaleView sView = new SaleView(sLogic);
            }
        });
        frame.add(saleButton);

        frame.setVisible(true);
    }
}
