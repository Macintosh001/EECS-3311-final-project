package project.display;

import project.logic.ILogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CouponManagerView extends MainDisplay{

    public CouponManagerView(ILogic logic){

        final String[] COLUMNS = {"Barcode", "Discount"};

        JFrame frame = new JFrame("Coupons");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        JTable table = new JTable(new DefaultTableModel(logic.getProductList().getTableEntries(), COLUMNS));
        table.setEnabled(false);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,300,400);
        frame.add(tablePane);

        JButton addButton = new JButton("Add Coupon");
        addButton.setBounds(350, 10, 200, 50);
        addButton.addActionListener(e -> new AddDialog(this));
        frame.add(addButton);

        JButton removeButton = new JButton("Remove Coupon");
        removeButton.setBounds(350, 70, 200, 50);
        removeButton.addActionListener(e -> new RemoveDialog(this));
        frame.add(removeButton);

        frame.setVisible(true);
    }
}
