package project.display;

import project.logic.ILogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainDisplay {

    private ILogic logic;
    private final String[] COLUMNS = {"Barcode", "Name", "Quantity", "Price", "Expiry Date"};
    private JTable table;

    public MainDisplay(ILogic logic) {

        this.logic = logic;

        // First we will construct all the UI elements.

        // This is the actual window.
        JFrame frame = new JFrame("T.I.M.");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280 + 2, 720 + 30);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        // This is the table containing all the products
        table = new JTable(new DefaultTableModel(logic.getProductList().getTableEntries(), COLUMNS));
        table.setEnabled(false);
        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10, 10, 800, 700);
        frame.add(tablePane);

        // This is the button to add products
        JButton addButton = new JButton("Add Product");
        addButton.setBounds(820, 10, 200, 50);
        addButton.addActionListener(e -> new AddDialog(this));
        frame.add(addButton);

        // This is the button to remove products
        JButton removeButton = new JButton("Remove Product");
        removeButton.setBounds(820, 70, 200, 50);
        removeButton.addActionListener(e -> new RemoveDialog(this));
        frame.add(removeButton);

        // This is the button to update products
        JButton updateButton = new JButton("Update Product");
        updateButton.setBounds(820, 130, 200, 50);
        updateButton.addActionListener(e -> new UpdateDialog(this));
        frame.add(updateButton);

        frame.setVisible(true);
    }

    public ILogic getLogic() {
        return logic;
    }

    public void regenTable() {
        table.setModel(new DefaultTableModel(logic.getProductList().getTableEntries(), COLUMNS));
    }

}
