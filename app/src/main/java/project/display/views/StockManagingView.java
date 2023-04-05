package project.display.views;

import com.github.lgooddatepicker.components.DatePicker;
import net.miginfocom.swing.MigLayout;
import project.display.Display;
import project.display.buttons.BackButton;
import project.display.dialogs.ErrorDialog;
import project.display.dialogs.RemoveStockDialog;
import project.display.views.builders.StockCheckingBuilder;
import project.logic.StockManagingLogic;
import project.objects.ErrorMsg;
import project.objects.Result;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class
StockManagingView extends JPanel implements ViewWithTable {

    private final String[] COLUMNS = {"Barcode", "Name", "Quantity", "Orig. Price", "Real Price", "Expiry Date"};
    private StockCheckingBuilder builder;
    private StockManagingLogic logic;
    private JTable table;

    /**
     * @param display represents the JFrame that contains this JPanel
     * @param logic reference to the Logic object which handles the functionality and data structures
     */
    public StockManagingView(Display display, StockManagingLogic logic) {

        super();
        builder = new StockCheckingBuilder();
        this.logic = logic;
        setBounds(0, 0, 1000, 700);
        setLayout(new BorderLayout());

        // Add all the UI Elements
        table = builder.buildTable(logic.getProductList(),COLUMNS);

        JPanel controlPanel = new JPanel(new MigLayout("insets 20 20 20 20, gap 30px"));

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,480,680);
        add(tablePane, BorderLayout.WEST);

        //Filter by label
        JLabel filterPriceLabel = builder.buildLabel("Filter by Price:");
        controlPanel.add(filterPriceLabel, "wrap");

        JLabel minPriceLabel = builder.buildLabel("Min:");
        controlPanel.add(minPriceLabel, "split 4, sg a");

        JTextField fPriceMinInput = builder.buildTextField();
        controlPanel.add(fPriceMinInput, "width 100::200, height :25:");

        JLabel maxPriceLabel = builder.buildLabel("Max:");
        controlPanel.add(maxPriceLabel, "sg a");

        JTextField fPriceMaxInput = builder.buildTextField();
        controlPanel.add(fPriceMaxInput, "wrap, width 100::200, height :25:");


        // Filter by Quantity
        JLabel filterQuantityLabel = builder.buildLabel("Filter by Quantity:");
        controlPanel.add(filterQuantityLabel, "wrap");

        JLabel minQuantityLabel = builder.buildLabel("Min:");
        controlPanel.add(minQuantityLabel, "split 4, sg a");

        JTextField fQuantityMinInput = builder.buildTextField();
        controlPanel.add(fQuantityMinInput, "width 100::200, height :25:");

        JLabel maxQuantityLabel = builder.buildLabel("Max:");
        controlPanel.add(maxQuantityLabel, "sg a");

        JTextField fQuantityMaxInput = builder.buildTextField();
        controlPanel.add(fQuantityMaxInput, "wrap, width 100::200, height :25:");

        JLabel filterDateLabel = builder.buildLabel("Filter by Expiry Date:");
        controlPanel.add(filterDateLabel, "wrap");

        JLabel minDateLabel = builder.buildLabel("Min:");
        controlPanel.add(minDateLabel, "split 2, sg a");

        //date picker set up with icon

        DatePicker datePicker = builder.buildDatePicker();
        controlPanel.add(datePicker, "wrap");

        JLabel maxDateLabel = builder.buildLabel("Max:");
        controlPanel.add(maxDateLabel, "split 2, sg a");

        DatePicker datePickerMax = builder.buildDatePicker();
        controlPanel.add(datePickerMax, "wrap");

        JButton confirmButton = builder.buildButton("Apply Filters");
        confirmButton.addActionListener(e -> {
            Result<String[][], List<ErrorMsg>> result = logic.getFilteredList(
                    fPriceMinInput.getText(),
                    fPriceMaxInput.getText(),
                    fQuantityMinInput.getText(),
                    fQuantityMaxInput.getText(),
                    datePicker.getDateStringOrEmptyString(),
                    datePickerMax.getDateStringOrEmptyString()
            );

            if (result.getError() != null) {
                new ErrorDialog(result.getError());
            } else {
                regenTable(result.getResult());
            }
        });
        controlPanel.add(confirmButton, "split 2, width :180:, height :80:, gapafter 30px");

        JButton removeButton = builder.buildButton("Remove Item");
        removeButton.addActionListener(e -> new RemoveStockDialog(this));
        controlPanel.add(removeButton, "wrap, width :180:, height :80:");
        BackButton backButton = new BackButton("Back", display);
        add(backButton);
        add(controlPanel, BorderLayout.CENTER);

        // Hide when initialized
        setVisible(false);
    }

    /**
     * @return a reference to the StockManagingLogic object
     */
    public StockManagingLogic getLogic() {
        return logic;
    }

    /**
     * @param entries 2D array that holds the data in the table
     */
    public void regenTable(String[][] entries){
        table.setModel(new DefaultTableModel(entries, COLUMNS));
    }

    /**
     * Responsible for regenerating a table when that table has been updated or altered
     */
    public void regenTable() {
        table.setModel(new DefaultTableModel(logic.getProductList(), COLUMNS));
    }
}
