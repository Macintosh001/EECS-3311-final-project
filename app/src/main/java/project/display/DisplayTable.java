package project.display;

import project.logic.ILogic;
import project.objects.ProductList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
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
        table1 = new JTable(new DefaultTableModel(data.getProductList().getTableEntries(),columnNames));
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
                dialog.setSize(600, 120);
                dialog.setLayout(new BorderLayout());

                JPanel fieldsPanel = new JPanel();
                fieldsPanel.setLayout(new GridLayout(2, 2));

                JLabel nameLabel = new JLabel("Name: ");
                nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                fieldsPanel.add(nameLabel);
                JTextField nameField = new JTextField();
                fieldsPanel.add(nameField);

                JLabel quantityLabel = new JLabel("Quantity: ");
                quantityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                fieldsPanel.add(quantityLabel);
                JTextField quantityField = new JTextField();
                fieldsPanel.add(quantityField);

                JLabel priceLabel = new JLabel("Price: ");
                priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                fieldsPanel.add(priceLabel);
                JTextField priceField = new JTextField();
                fieldsPanel.add(priceField);

                JLabel expiryLabel = new JLabel("Expiry Date: ");
                expiryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                fieldsPanel.add(expiryLabel);
                JTextField expiryField = new JTextField();
                expiryField.setToolTipText("yyyy-mm-dd");  //Gives a quick hint to input dates
                fieldsPanel.add(expiryField);

                dialog.add(fieldsPanel, BorderLayout.NORTH);

                JLabel errorLabel = new JLabel("");
                JButton submitButton = new JButton("Submit");
                JButton cancelButton = new JButton("Cancel");
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // This method with read and then validate the inputs from the text fields
                        // presented in the dialog box. If there is an error, the errorLabel will
                        // be set to the error message.

                        // If we get to the end of the method, and the error label is empty, that
                        // means there were no errors, and we can add the product. Otherwise,
                        // nothing happens and the user can make changes to the fields based on
                        // the displayed error message.

                        // These are the fields the user can set
                        String name = "";
                        int quantity = 0;
                        float price = 0.0f;
                        Date date = null;

                        // Reset error label
                        errorLabel.setText("");

                        // Validate the name field
                        name = nameField.getText();
                        if (name.equals("")) {
                            errorLabel.setText("ERROR: Name cannot be empty!");
                        }

                        // Validate the quantity field
                        // i.e. the quantity cannot be empty, negative and it must be a whole number.
                        if (quantityField.getText().equals("")) {
                            errorLabel.setText("ERROR: Quantity cannot be empty!");
                        } else {
                            try {
                                quantity = Integer.parseInt(quantityField.getText());
                                if (quantity < 0) {
                                    errorLabel.setText("ERROR: Quantity cannot be negative!");
                                }
                            } catch (NumberFormatException ex) {
                                errorLabel.setText("ERROR: Quantity must be a whole number!");
                            }
                        }
                        
                        // Checkes whether the Price input is correct
                        // i.e. whether is blank, negative or is not a decimal number.
                        if (priceField.getText().equals("")) {
                           errorLabel.setText("ERROR: Price cannot be blank!");
                        } else {
                            try {
                                price = Float.parseFloat(priceField.getText());
                                if (price < 0) {
                                    errorLabel.setText("ERROR: Price cannot be negative!");
                                }
                            } catch (NumberFormatException ex) {
                                errorLabel.setText("ERROR: Price must be a decimal number!");
                            }
                        }

                        // Date and the Expiry date is being implemented here.
                        // The right format of the date-input should be displayed for the client.
                        // the proper format is: yyyy-mm-dd


                        String dateF = expiryField.getText();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        JFormattedTextField dField = new JFormattedTextField(dateFormat);

                        try {
                            date = dateFormat.parse(dateF);
                        } catch(ParseException t) {
                            errorLabel.setText("ERROR: Date invalid. yyyy-mm-dd");
                        }


                        // Only exit the dialog when there are no errors.
                        if (errorLabel.getText().equals("")) {
                            data.addProduct(name, quantity, price, date);
                            table1.setModel(new DefaultTableModel(data.getProductList().getTableEntries(), columnNames));
                            dialog.dispose();
                        }
                    }
                });

                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                    }
                });

                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new GridLayout(1, 3));
                buttonPanel.add(submitButton);
                buttonPanel.add(cancelButton);
                buttonPanel.add(errorLabel);

                dialog.add(buttonPanel, BorderLayout.AFTER_LAST_LINE);
                dialog.setVisible(true);
            }
        });

        // The remove button, in order to the remove a product from the list
        // Once the client inputs the proper ID,barcode, the product will be removed.

        removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog();
                dialog.setTitle("Input");
                dialog.setSize(150, 100);
                dialog.setLayout(new BorderLayout());

                JPanel fieldsPanel = new JPanel();
                fieldsPanel.setLayout(new GridLayout(1, 1));

                // for the barcode label
                JLabel barcodeLabel = new JLabel("Barcode: ");
                barcodeLabel.setHorizontalAlignment(SwingConstants.LEFT);
                fieldsPanel.add(barcodeLabel);
                JTextField barcodeField = new JTextField();
                barcodeField.setHorizontalAlignment(SwingConstants.LEFT);
                fieldsPanel.add(barcodeField);


                dialog.add(fieldsPanel, BorderLayout.PAGE_START);


                // submit button to remove the product from the list
                JButton submitButton = new JButton("Submit");
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        int barcode = Integer.parseInt(barcodeField.getText());

                        data.removeProduct(barcode);
                        table1.setModel(new DefaultTableModel(data.getProductList().getTableEntries(), columnNames));
                        dialog.dispose();
                    }
                });

                JPanel buttonPanel = new JPanel();
                buttonPanel.add(submitButton);

                dialog.add(buttonPanel, BorderLayout.PAGE_END);
                dialog.setVisible(true);
            }
        });
        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        panel1.add(buttonsPanel, BorderLayout.PAGE_END);
        frame.add(panel1);
        frame.setVisible(true);
    }
}
