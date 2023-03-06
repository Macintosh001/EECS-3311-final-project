package project.display;

import project.display.dialog.ApplyCouponDialog;
import project.display.dialog.BuyDialog;
import project.display.dialog.ScanDialog;
import project.logic.SaleLogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SaleView extends MainDisplay{

    private SaleLogic sLogic = null;
    private final String[] COLUMNS = {"Barcode", "Name", "Quantity", "Price"};
    private JTable table;

    public SaleView(SaleLogic sLogic){

        this.sLogic = sLogic;

        JFrame frame = new JFrame("POS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        table = new JTable(new DefaultTableModel(sLogic.getCartTable(), COLUMNS));
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

        JButton clearButton = new JButton("Clear Cart");
        clearButton.setBounds(350, 190, 200, 50);
        clearButton.addActionListener(e -> new BuyDialog(this));
        frame.add(clearButton);

        frame.setVisible(true);
    }

    public SaleLogic getsLogic(){
        return sLogic;
    }

    public void regenTable(){
        table.setModel(new DefaultTableModel(sLogic.getCartTable(), COLUMNS));
    }
}
