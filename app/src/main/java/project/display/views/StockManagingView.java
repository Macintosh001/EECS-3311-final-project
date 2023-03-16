package project.display.views;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.demo.FullDemo;
import project.display.Display;
import project.display.buttons.BackButton;
import project.display.dialogs.ErrorDialog;
import project.display.dialogs.RemoveStockDialog;
import project.logic.StockManagingLogic;
import project.objects.ErrorMsg;
import project.objects.Result;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class
StockManagingView extends JPanel implements ViewWithTable {
    private StockManagingLogic logic;
    private final String[] COLUMNS = {"Barcode", "Name", "Quantity", "Price", "Expiry Date"};
    private JTable table;

    /**
     * @param display represents the JFrame that contains this JPanel
     * @param logic reference to the Logic object which handles the functionality and data structures
     */
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

        JLabel filterDateLabel = new JLabel("Filter by Expiry Date:");
        filterDateLabel.setBounds(700, 190, 200, 50);
        add(filterDateLabel);

        JLabel minDateLabel = new JLabel("Min:");
        minDateLabel.setBounds(570, 240, 100, 50);
        add(minDateLabel);

        //date picker set up with icon
        URL dateImageURL = FullDemo.class.getResource("/images/datepickerbutton1.png");
        Image dateExampleImage = Toolkit.getDefaultToolkit().getImage(dateImageURL);
        ImageIcon dateExampleIcon = new ImageIcon(dateExampleImage);

        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.getEnableYearMenu();
        DatePicker datePicker = new DatePicker(dateSettings);
        JButton datePickerButton = datePicker.getComponentToggleCalendarButton();
        datePickerButton.setText("");
        datePickerButton.setIcon(dateExampleIcon);
        datePicker.setBounds(620,240,200,50);
        add(datePicker);


//        JTextField fDateMinInput = new JTextField();
//        fDateMinInput.setBounds(620, 240, 100, 50);
//        add(fDateMinInput);

        JLabel maxDateLabel = new JLabel("Max:");
        maxDateLabel.setBounds(570, 325, 100, 50);
        add(maxDateLabel);

        DatePicker datePickerMax = new DatePicker();
        JButton datePickerButton2 = datePickerMax.getComponentToggleCalendarButton();
        datePickerButton2.setText("");
        datePickerButton2.setIcon(dateExampleIcon);
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
                    datePicker.getDateStringOrEmptyString(), datePickerMax.getDateStringOrEmptyString()
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
