package project.display.views;

import project.display.Display;
import project.display.buttons.BackButton;
import project.display.dialogs.ErrorDialog;
import project.display.dialogs.RemoveStockDialog;
import project.logic.StockCheckingLogic;
import project.logic.StockManagingLogic;
import project.objects.ErrorMsg;
import project.objects.Result;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class StockManagingView extends JPanel implements ViewWithTable {
    private StockManagingLogic logic;
    private final String[] COLUMNS = {"Barcode", "Name", "Price", "Quantity", "Expiry Date"};
    private JTable table;

    public StockManagingView(Display display, StockManagingLogic logic) {

        super();
        this.logic = logic;
        setBounds(0, 0, 1000, 700);
        setLayout(null);

        // Add all the UI Elements
        table = new JTable(new DefaultTableModel(this.logic.getProductList(), COLUMNS));
        table.setEnabled(false);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,480,680);
        add(tablePane);

        //Filter by label
        JLabel filterPriceLabel = new JLabel("Filter by Price:");
        filterPriceLabel.setBounds(700, 10, 200, 50);
        add(filterPriceLabel);

        JLabel minPriceLabel = new JLabel("Min:");
        minPriceLabel.setBounds(570, 60, 100, 50);
        add(minPriceLabel);

        JTextField fPriceMinInput = new JTextField();
        fPriceMinInput.setBounds(620, 60, 100, 50);
        add(fPriceMinInput);

        JLabel maxPriceLabel = new JLabel("Max:");
        maxPriceLabel.setBounds(750, 60, 100, 50);
        add(maxPriceLabel);

        JTextField fPriceMaxInput = new JTextField();
        fPriceMaxInput.setBounds(800, 60, 100, 50);
        add(fPriceMaxInput);


        // Filter by Quantity
        JLabel filterQuantityLabel = new JLabel("Filter by Quantity:");
        filterQuantityLabel.setBounds(700, 100, 200, 50);
        add(filterQuantityLabel);

        JLabel minQuantityLabel = new JLabel("Min:");
        minQuantityLabel.setBounds(570, 150, 100, 50);
        add(minQuantityLabel);

        JTextField fQuantityMinInput = new JTextField();
        fQuantityMinInput.setBounds(620, 150, 100, 50);
        add(fQuantityMinInput);

        JLabel maxQuantityLabel = new JLabel("Max:");
        maxQuantityLabel.setBounds(750, 150, 100, 50);
        add(maxQuantityLabel);

        JTextField fQuantityMaxInput = new JTextField();
        fQuantityMaxInput.setBounds(800, 150, 100, 50);
        add(fQuantityMaxInput);

        JButton confirmButton = new JButton("Apply Filters");
        confirmButton.setBounds(810, 310, 180, 80);
        confirmButton.addActionListener(e -> {
            Result<String[][], List<ErrorMsg>> result = logic.getFilteredList(
                    fPriceMinInput.getText(),
                    fPriceMaxInput.getText(),
                    fQuantityMinInput.getText(),
                    fQuantityMaxInput.getText(),
                    "", ""
            );

            if (result.getError() != null) {
                new ErrorDialog(result.getError());
            } else {
                regenTable(result.getResult());
            }
        });
        add(confirmButton);

        // Button to remove an item from stock
        JButton removeButton = new JButton("Remove Item");
        removeButton.setBounds(510, 510,180,80);
        removeButton.addActionListener(e -> new RemoveStockDialog(this));
        add(removeButton);

        BackButton backButton = new BackButton("Back", display);
        add(backButton);

        // Hide when initialized
        setVisible(false);
    }

    public StockManagingLogic getLogic() {
        return logic;
    }

    public void regenTable(String[][] entries){
        table.setModel(new DefaultTableModel(entries, COLUMNS));
    }

    public void regenTable() {
        table.setModel(new DefaultTableModel(logic.getProductList(), COLUMNS));
    }
}
