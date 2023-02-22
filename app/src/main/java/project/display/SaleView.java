package project.display;

import project.logic.ILogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SaleView extends MainDisplay{

    public SaleView(ILogic logic){

        final String[] COLUMNS = {"Barcode", "Name", "Quantity", "Price"};

        JFrame frame = new JFrame("POS");
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

        JButton scanButton = new JButton("Scan");
        scanButton.setBounds(350, 10, 200, 50);
        scanButton.addActionListener(e -> new ScanDialog(this));
        frame.add(scanButton);

        JButton applyCouponButton = new JButton("Apply Coupon");
        applyCouponButton.setBounds(350, 70, 200, 50);
        applyCouponButton.addActionListener(e -> new ApplyCouponDialog(this));
        frame.add(applyCouponButton);

        JButton buyButton = new JButton("Buy");
        buyButton.setBounds(350, 130, 200, 50);
        buyButton.addActionListener(e -> new BuyDialog(this));
        frame.add(buyButton);

        frame.setVisible(true);
    }
}
