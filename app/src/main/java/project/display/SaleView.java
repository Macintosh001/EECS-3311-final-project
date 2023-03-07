package project.display;

import project.display.dialog.sale_dialog.ApplyCouponDialog;
import project.display.dialog.sale_dialog.BuyDialog;
import project.display.dialog.sale_dialog.ScanDialog;
import project.logic.SaleLogic;
import project.objects.ErrorMsg;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SaleView {

    private SaleLogic sLogic = null;
    private final String[] COLUMNS = {"Name", "Price"};
    private final JTable table;

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
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ErrorMsg> errorMsgList = sLogic.buy();
            }
        });
        frame.add(buyButton);

        JButton clearButton = new JButton("Clear Cart");
        clearButton.setBounds(350, 190, 200, 50);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sLogic.clearShoppingCart();
            }
        });
        frame.add(clearButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(350,350,200,50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        frame.add(backButton);

        frame.setVisible(true);
    }

    public SaleLogic getsLogic(){
        return sLogic;
    }

    public void regenTable(){
        table.setModel(new DefaultTableModel(sLogic.getCartTable(), COLUMNS));
    }
}
