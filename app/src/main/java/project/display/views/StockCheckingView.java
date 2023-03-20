package project.display.views;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.demo.FullDemo;
import project.display.Display;
import project.display.buttons.BackButton;
import project.display.dialogs.ErrorDialog;
import project.display.views.builders.Builder;
import project.display.views.builders.StockCheckingBuilder;
import project.display.views.builders.StockManagingBuilder;
import project.logic.StockCheckingLogic;
import project.objects.ErrorMsg;
import project.objects.Result;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class StockCheckingView extends JPanel implements ViewWithTable {

    private StockCheckingBuilder builder;
    private StockCheckingLogic logic;
    private JTable table;
    /**
     * @param display represents the JFrame that contains this JPanel
     * @param logic reference to the Logic object which handles the functionality and data structures
     */
    public StockCheckingView(Display display, StockCheckingLogic logic) {

        super();
        this.logic = logic;
        builder = new StockCheckingBuilder();
        setBounds(0, 0, 1000, 700);
        setLayout(null);

        table = builder.buildTable(logic.getProductList());

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,480,680);
        add(tablePane);

        //Filter by label
        JLabel filterPriceLabel = builder.buildLabel("Filter by Price:");
        filterPriceLabel.setBounds(700, 10, 200, 50);
        add(filterPriceLabel);

        JLabel minPriceLabel = builder.buildLabel("Min");
        minPriceLabel.setBounds(570, 60, 100, 50);
        add(minPriceLabel);

        JTextField fPriceMinInput = builder.buildTextField();
        fPriceMinInput.setBounds(620, 60, 100, 50);
        add(fPriceMinInput);

        JLabel maxPriceLabel = builder.buildLabel("Max");
        maxPriceLabel.setBounds(750, 60, 100, 50);
        add(maxPriceLabel);

        JTextField fPriceMaxInput = builder.buildTextField();
        fPriceMaxInput.setBounds(800, 60, 100, 50);
        add(fPriceMaxInput);


        // Filter by Quantity
        JLabel filterQuantityLabel = builder.buildLabel("Filter by Quantity:");
        filterQuantityLabel.setBounds(700, 100, 200, 50);
        add(filterQuantityLabel);

        JLabel minQuantityLabel = builder.buildLabel("Min:");
        minQuantityLabel.setBounds(570, 150, 100, 50);
        add(minQuantityLabel);

        JTextField fQuantityMinInput = builder.buildTextField();
        fQuantityMinInput.setBounds(620, 150, 100, 50);
        add(fQuantityMinInput);

        JLabel maxQuantityLabel = builder.buildLabel("Max:");
        maxQuantityLabel.setBounds(750, 150, 100, 50);
        add(maxQuantityLabel);

        JTextField fQuantityMaxInput = builder.buildTextField();
        fQuantityMaxInput.setBounds(800, 150, 100, 50);
        add(fQuantityMaxInput);

        JLabel filterDateLabel = builder.buildLabel("Filter by Expiry Date:");
        filterDateLabel.setBounds(700, 190, 200, 50);
        add(filterDateLabel);

        JLabel minDateLabel = builder.buildLabel("Min:");
        minDateLabel.setBounds(570, 240, 100, 50);
        add(minDateLabel);

        //date picker set up with icon
        DatePicker datePicker = builder.buildDatePicker();
        datePicker.setBounds(620,240,200,50);
        add(datePicker);


        JLabel maxDateLabel = builder.buildLabel("Max:");
        maxDateLabel.setBounds(570, 325, 100, 50);
        add(maxDateLabel);

        DatePicker datePickerMax = builder.buildDatePicker();
        datePickerMax.setBounds(620,325,200,50);
        add(datePickerMax);

        JButton confirmButton = new JButton("Apply Filters");
        confirmButton.setBounds(810, 310, 180, 80);
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
        add(confirmButton);

        BackButton backButton = new BackButton("Back", display);
        add(backButton);

        // Hide when initialized
        setVisible(false);
    }

    /**
     * @return a reference to the StockCheckingLogic object
     */
    public StockCheckingLogic getLogic() {
        return logic;
    }

    /**
     * Responsible for regenerating the table
     */
    public void regenTable(String[][] entries){
        table.setModel(new DefaultTableModel(entries, builder.getColumns()));
    }

    /**
     * Responsible for regenerating the table
     */
    public void regenTable() {
        table.setModel(new DefaultTableModel
                (logic.getProductList(), builder.getColumns()));
    }
}
