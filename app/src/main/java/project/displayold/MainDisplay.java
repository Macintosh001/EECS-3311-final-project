package project.displayold;

import project.displayold.dialog.AddDialog;
import project.displayold.dialog.UpdateDialog;

import javax.swing.*;

public class MainDisplay {

    private final String[] COLUMNS = {"Barcode", "Name", "Quantity", "Price", "Expiry Date"};
    //private final JTable table;


    public MainDisplay() {

        // First we will construct all the UI elements.

        // This is the actual window.
        JFrame frame = new JFrame("T.I.M.");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1050, 720);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        // This is the table containing all the products
        //table = new JTable(new DefaultTableModel(logic.getProductList().getTableEntries(), COLUMNS));
        //table.setEnabled(false);
        //table.setBackground(Color.LIGHT_GRAY);
        //JScrollPane tablePane = new JScrollPane(table);
        //tablePane.setBounds(10, 10, 800, 700);
        //frame.add(tablePane);

        // This is the button to add products
        JButton addButton = new JButton("Add Product");
        addButton.setBounds(820, 10, 200, 50);
        addButton.addActionListener(e -> new AddDialog(this));
        frame.add(addButton);

        // This is the button to remove products
        JButton removeButton = new JButton("Remove Product");
        removeButton.setBounds(820, 70, 200, 50);
        frame.add(removeButton);

        // This is the button to update products
        JButton updateButton = new JButton("Update Product");
        updateButton.setBounds(820, 130, 200, 50);
        updateButton.addActionListener(e -> new UpdateDialog(this));
        frame.add(updateButton);

        frame.setVisible(true);
    }

    //public JTable getTable(){
        //return this.table;
    //}

    //public ILogic getLogic() {
      //  return logic;
    //}

    //public void regenTable() {
      //  table.setModel(new DefaultTableModel(logic.getProductList().getTableEntries(), COLUMNS));
    //}

}
