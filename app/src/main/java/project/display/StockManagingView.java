package project.display;

import project.display.dialog.RemoveStockDialog;
import project.logic.StockCheckingLogic;
import project.logic.StockManagingLogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StockManagingView {

    private StockManagingLogic smLogic = null;
    private JTable table;
    private final String[] COLUMNS = {"Barcode", "Name", "Price", "Quantity", "Expiry Date"};
    public StockManagingView(StockManagingLogic smLogic){

        this.smLogic = smLogic;

        JFrame frame = new JFrame("Stock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        table = new JTable(new DefaultTableModel(smLogic.getProductList(), COLUMNS));
        table.setEnabled(false);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,300,400);
        frame.add(tablePane);

        JButton removeButton = new JButton("Remove Item");
        removeButton.setBounds(350,70,200,50);
        removeButton.addActionListener(e -> new RemoveStockDialog(this));
        frame.add(removeButton);

        frame.setVisible(true);
    }

    public StockManagingLogic getsmLogic() {
        return smLogic;
    }

    public void regenTable() {
            table.setModel(new DefaultTableModel(smLogic.getProductList(), COLUMNS));
    }
}
