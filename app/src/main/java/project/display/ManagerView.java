package project.display;

import project.display.dialog.AddDialog;
import project.logic.*;
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
public class ManagerView extends InitialDisplay{

    private CouponManagerLogic cpLogic = null;
    private StockManagingLogic smLogic = null;
    private OrderLogic oLogic = null;

    public ManagerView(StockCheckingLogic scLogic, SaleLogic sLogic,
                       CouponManagerLogic cLogic, StockManagingLogic smLogic, OrderLogic oLogic){

        super(scLogic,sLogic,cLogic,smLogic,oLogic);
        this.cpLogic = cLogic;
        this.smLogic = smLogic;
        this.oLogic = oLogic;

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
                CouponManagerView cView = new CouponManagerView(scLogic,sLogic,cLogic,smLogic,oLogic);
                frame.dispose();
            }
        });
        frame.add(couponButton);

        JButton stockButton = new JButton("Stock");
        stockButton.setBounds(200, 70, 200, 50);
        stockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockManagingView smView = new StockManagingView(scLogic,sLogic,cLogic,smLogic,oLogic);
                frame.dispose();
            }
        });
        frame.add(stockButton);

        JButton orderButton = new JButton("Order");
        orderButton.setBounds(200, 130, 200, 50);
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderView oView = new OrderView(scLogic,sLogic,cLogic,smLogic,oLogic);
                frame.dispose();
            }
        });
        frame.add(orderButton);

        frame.setVisible(true);
    }
}
