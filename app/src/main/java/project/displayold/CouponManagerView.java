package project.displayold;

import project.display.dialogs.coupon_dialog.AddCouponDialog;
import project.display.dialogs.coupon_dialog.RemoveCouponDialog;
import project.logic.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CouponManagerView extends InitialDisplay{

    private CouponManagerLogic cpLogic = null;
    private final JTable table;
    final String[] COLUMNS = {"Barcode", "Discount"};

    public CouponManagerView(StockCheckingLogic scLogic, SaleLogic sLogic,
                             CouponManagerLogic cLogic, StockManagingLogic smLogic, OrderLogic oLogic){

        super(scLogic,sLogic,cLogic,smLogic,oLogic);
        this.cpLogic = cpLogic;


        JFrame frame = new JFrame("Coupons");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        table = new JTable(new DefaultTableModel(cpLogic.getCouponTable(), COLUMNS));
        table.setEnabled(false);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,300,400);
        frame.add(tablePane);

        JButton addButton = new JButton("Add Coupon");
        addButton.setBounds(350, 10, 200, 50);
//        addButton.addActionListener(e -> new AddCouponDialog(this));
        frame.add(addButton);

        JButton removeButton = new JButton("Remove Coupon");
        removeButton.setBounds(350, 70, 200, 50);
//        removeButton.addActionListener(e -> new RemoveCouponDialog(this));
        frame.add(removeButton);

        JButton updateButton = new JButton("Update Coupon");
        updateButton.setBounds(350, 130, 200, 50);
//        updateButton.addActionListener(e -> new RemoveCouponDialog(this));
        frame.add(updateButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(350,350,200,50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManagerView mView = new ManagerView(scLogic,sLogic,cLogic,smLogic,oLogic);
                frame.dispose();
            }
        });
        frame.add(backButton);

        frame.setVisible(true);
    }

    public CouponManagerLogic getCpLogic(){
        return cpLogic;
    }

    public void regenTable(){
        table.setModel(new DefaultTableModel(cpLogic.getCouponTable(), COLUMNS));
    }
}
