package project.display;

import project.logic.ILogic;
import project.objects.ProductList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog();
                dialog.setTitle("Input");
                dialog.setSize(400, 200);
                dialog.setLayout(new BorderLayout());

                JPanel fieldsPanel = new JPanel();
                fieldsPanel.setLayout(new GridLayout(2, 2));

                JLabel nameLabel = new JLabel("Name: ");
                fieldsPanel.add(nameLabel);
                JTextField nameField = new JTextField();
                fieldsPanel.add(nameField);

                JLabel quantityLabel = new JLabel("Quantity: ");
                fieldsPanel.add(quantityLabel);
                JTextField quantityField = new JTextField();
                fieldsPanel.add(quantityField);

                JLabel priceLabel = new JLabel("Price: ");
                fieldsPanel.add(priceLabel);
                JTextField priceField = new JTextField();
                fieldsPanel.add(priceField);

                JLabel expiryLabel = new JLabel("Expiry Date: ");
                fieldsPanel.add(expiryLabel);
                JTextField expiryField = new JTextField();
                fieldsPanel.add(expiryField);


                dialog.add(fieldsPanel, BorderLayout.CENTER);

                JButton submitButton = new JButton("Submit");
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText();
                        int quantity = Integer.parseInt(quantityField.getText());
                        float price = Float.parseFloat(priceField.getText());
                        String dateF = expiryField.getText();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        Date date = null;
                        try {
                            date = dateFormat.parse(dateF);
                        } catch (ParseException t) {
                            t.printStackTrace();
                        }
                        data.addProduct(name,quantity, price, new Date());
                        dialog.dispose();
                    }
                });

                JPanel buttonPanel = new JPanel();
                buttonPanel.add(submitButton);

                dialog.add(buttonPanel, BorderLayout.PAGE_END);
                dialog.setVisible(true);
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
