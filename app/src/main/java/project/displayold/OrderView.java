package project.displayold;

import project.displayold.dialog.order_dialog.*;
import project.logic.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
This view should perhaps have a view of the inventory in a table format
an order button and a cancel button
 */
public class OrderView extends InitialDisplay{

    private OrderLogic oLogic = null;
    private final JTable table;
    final String[] COLUMNS = {"Name", "Price", "Shelf Life"};

    public OrderView (StockCheckingLogic scLogic, SaleLogic sLogic,
                      CouponManagerLogic cLogic, StockManagingLogic smLogic, OrderLogic oLogic){

        super(scLogic,sLogic,cLogic,smLogic,oLogic);
        this.oLogic = oLogic;

        JFrame frame = new JFrame("Orders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1050,720);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        table = new JTable(new DefaultTableModel(oLogic.getOrderableList(), COLUMNS));
        table.setEnabled(false);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,600,650);
        frame.add(tablePane);

        // This is the button to add products
        JButton orderButton = new JButton("Order");
        orderButton.setBounds(720, 10, 200, 50);
        orderButton.addActionListener(e -> new PlaceOrderDialog(this));
        frame.add(orderButton);

        // This is the button to remove products
        JButton updatePriceButton = new JButton("Update Price");
        updatePriceButton.setBounds(720, 70, 200, 50);
        updatePriceButton.addActionListener(e -> new UpdatePriceDialog(this));
        frame.add(updatePriceButton);

        // This is the button to update products
        JButton updateButton = new JButton("Update shelf life");
        updateButton.setBounds(720, 130, 200, 50);
        updateButton.addActionListener(e -> new UpdateShelfLifeDialog(this));
        frame.add(updateButton);

        JButton addButton = new JButton("Add");
        addButton.setBounds(720, 190, 200, 50);
        addButton.addActionListener(e -> new AddOrderDialog(this));
        frame.add(addButton);

        JButton removeButton = new JButton("Remove");
        removeButton.setBounds(720, 250, 200, 50);
        removeButton.addActionListener(e -> new RemoveOrderDialog(this));
        frame.add(removeButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(720,600,200,50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManagerView mView = new ManagerView(scLogic,sLogic,cLogic,smLogic,oLogic);
            }
        });
        frame.add(backButton);

        frame.setVisible(true);
    }

    public OrderLogic getOLogic(){
        return oLogic;
    }

    public void regenTable(){
        table.setModel(new DefaultTableModel(oLogic.getOrderableList(), COLUMNS));
    }

}
