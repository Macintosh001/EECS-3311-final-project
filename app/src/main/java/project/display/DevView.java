package project.display;

import project.logic.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DevView {

    public CouponManagerLogic cLogic = null;
    public StockManagingLogic smLogic = null;
    public StockCheckingLogic scLogic = null;
    public OrderLogic oLogic = null;
    public SaleLogic sLogic = null;
    public DevView(CouponManagerLogic cLogic, StockManagingLogic smLogic,
                   OrderLogic oLogic, StockCheckingLogic scLogic, SaleLogic sLogic){

        JFrame frame = new JFrame("Dev/QA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        JButton couponButton = new JButton("Coupons");
        couponButton.setBounds(200, 10, 200, 50);
        couponButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CouponManagerView cView = new CouponManagerView(cLogic);
                frame.dispose();
            }
        });
        frame.add(couponButton);

        JButton stockButton = new JButton("Stock");
        stockButton.setBounds(200, 70, 200, 50);
        stockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockManagingView smView = new StockManagingView(smLogic);
                frame.dispose();
            }
        });
        frame.add(stockButton);

        JButton orderButton = new JButton("Order");
        orderButton.setBounds(200, 130, 200, 50);
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderView oView = new OrderView(oLogic);
                frame.dispose();
            }
        });
        frame.add(orderButton);

        JButton stockCheckButton = new JButton("Stock Check");
        stockCheckButton.setBounds(200, 190, 200, 50);
        stockCheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockCheckingView scView = new StockCheckingView(scLogic);
                frame.dispose();
            }
        });
        frame.add(stockCheckButton);

        JButton posButton = new JButton("POS");
        posButton.setBounds(200, 250, 200, 50);
        posButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaleView sView = new SaleView(sLogic);
                frame.dispose();
            }
        });
        frame.add(posButton);

        frame.setVisible(true);
    }
}
