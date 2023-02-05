package project.display;

import project.logic.ILogic;
import project.objects.ProductList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class DisplayTable {

    /* This class is responsible for displaying current inventory data
     */
    private final String[] columnNames = { "ID", "Item name", "Quantity", "Price", "Expiry Date" };
    private ProductList productList;
    private JTable table1;
    private JPanel panel1;
    private JPanel buttonsPanel;
    private JFrame frame;
    private JButton addButton; // will be mapped to an addProduct operation that adds a Product to the ProductList
    private JButton removeButton; // will be mapped to a removeProduct operation that removes a product from the list
    @SuppressWarnings("BoundFieldAssignment")
    public DisplayTable(ILogic data){

        frame = new JFrame();
        frame.setTitle("Basic Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // initializing the JTable
        //noinspection BoundFieldAssignment
        table1 = new JTable(data.getProductList().getTableEntries(), columnNames);
        table1.setBounds(30,40,200,300);
        // adding the JTable to the scroll pane
        panel1 = new JPanel(new BorderLayout());
        panel1.add(new JScrollPane(table1), BorderLayout.CENTER);
        //frame size
        frame.setSize(500,200);
        // make frame visible

        // JPanel buttonsPanel configuration, need a separate panel to help with organizing layout
        buttonsPanel = new JPanel();
        /*
        add and remove buttons below
        each takes an ActionListener which is responsible for implement the
        actionPerformed(ActionEvent e) method
        inside is where we can call our add and remove methods
         */
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener(){ // currently doesn't do anything even dummy data
            @Override
            public void actionPerformed(ActionEvent e) {
                data.addProduct("dummy", 0,0f, new Date());
            }
        });

        removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener(){ // currently doesn't do anything
            @Override
            public void actionPerformed(ActionEvent e) {
                data.removeProduct(1);
            }
        });
        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        panel1.add(buttonsPanel, BorderLayout.PAGE_END);
        frame.add(panel1);
        frame.setVisible(true);
    }
}
