package project.display;

import project.display.dialog.AddDialog;
import project.logic.CouponManagerLogic;
import project.logic.OrderLogic;
import project.logic.StockManagingLogic;
import project.persistence.CouponDatabase;
import project.persistence.OrderableDatabase;
import project.persistence.ProductDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
This view pertains to all the controls and data a manager should have access too
general view of the inventory in a table format
buttons representing operations that the manager may want to use
 */
public class ManagerView {

    public ManagerView(){

        JFrame frame = new JFrame("Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,250);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        JButton couponButton = new JButton("Coupons");
        couponButton.setBounds(200, 10, 200, 50);
        couponButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CouponDatabase cdb = null;
                CouponManagerLogic cLogic = new CouponManagerLogic(cdb);
                CouponManagerView cView = new CouponManagerView(cLogic) ;
            }
        });
        frame.add(couponButton);

        JButton stockButton = new JButton("Stock");
        stockButton.setBounds(200, 70, 200, 50);
        stockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductDatabase pdb = null;
                StockManagingLogic smLogic = new StockManagingLogic(pdb);
                StockManagingView smView = new StockManagingView(smLogic);
            }
        });
        frame.add(stockButton);

        JButton orderButton = new JButton("Order");
        orderButton.setBounds(200, 130, 200, 50);
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderableDatabase odb = null;
                ProductDatabase pdb = null;
                OrderLogic oLogic = new OrderLogic(pdb,odb);
                OrderView oView = new OrderView(oLogic);
            }
        });
        frame.add(orderButton);

        frame.setVisible(true);
    }
}
