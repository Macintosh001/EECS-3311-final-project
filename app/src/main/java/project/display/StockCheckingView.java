package project.display;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.demo.FullDemo;
import project.logic.StockCheckingLogic;
import project.objects.ErrorMsg;
import project.objects.Result;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

public class StockCheckingView {

    private StockCheckingLogic scLogic = null;
    private JTable table;
    private final String[] COLUMNS = {"Barcode", "Name", "Price", "Quantity", "Expiry Date"};

    public StockCheckingView(StockCheckingLogic scLogic){

        this.scLogic = scLogic;

        JFrame frame = new JFrame("Stock Checking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1050,720);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        table = new JTable(new DefaultTableModel(scLogic.getProductList(), COLUMNS));
        table.setEnabled(false);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,500,400);
        frame.add(tablePane);

        //Filter by label
        JLabel filterPriceLabel = new JLabel("Filter by Price:");
        filterPriceLabel.setBounds(700, 10, 200, 50);
        frame.add(filterPriceLabel);

        JLabel minPriceLabel = new JLabel("Min:");
        minPriceLabel.setBounds(570, 60, 100, 50);
        frame.add(minPriceLabel);

        JTextField fPriceMinInput = new JTextField();
        fPriceMinInput.setBounds(620, 60, 100, 50);
        frame.add(fPriceMinInput);

        JLabel maxPriceLabel = new JLabel("Max:");
        maxPriceLabel.setBounds(750, 60, 100, 50);
        frame.add(maxPriceLabel);

        JTextField fPriceMaxInput = new JTextField();
        fPriceMaxInput.setBounds(800, 60, 100, 50);
        frame.add(fPriceMaxInput);


        // Filter by Quantity
        JLabel filterQuantityLabel = new JLabel("Filter by Quantity:");
        filterQuantityLabel.setBounds(700, 100, 200, 50);
        frame.add(filterQuantityLabel);

        JLabel minQuantityLabel = new JLabel("Min:");
        minQuantityLabel.setBounds(570, 150, 100, 50);
        frame.add(minQuantityLabel);

        JTextField fQuantityMinInput = new JTextField();
        fQuantityMinInput.setBounds(620, 150, 100, 50);
        frame.add(fQuantityMinInput);

        JLabel maxQuantityLabel = new JLabel("Max:");
        maxQuantityLabel.setBounds(750, 150, 100, 50);
        frame.add(maxQuantityLabel);

        JTextField fQuantityMaxInput = new JTextField();
        fQuantityMaxInput.setBounds(800, 150, 100, 50);
        frame.add(fQuantityMaxInput);


        // Filter by Date
        JLabel filterDateLabel = new JLabel("Filter by Expiry Date:");
        filterDateLabel.setBounds(700, 190, 200, 50);
        frame.add(filterDateLabel);

        JLabel minDateLabel = new JLabel("Start:");
        // add date picker with an icon for the button
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.getEnableYearMenu();
        DatePicker minDatePicker = new DatePicker(dateSettings);
        URL dateImageURL = FullDemo.class.getResource("/images/datepickerbutton1.png");
        Image dateExampleImage = Toolkit.getDefaultToolkit().getImage(dateImageURL);
        ImageIcon dateExampleIcon = new ImageIcon(dateExampleImage);
        JButton datePickerButton = minDatePicker.getComponentToggleCalendarButton();
        datePickerButton.setText("");
        datePickerButton.setIcon(dateExampleIcon);
        minDatePicker.setBounds(620,240,100,50);
        frame.add(minDatePicker);

//        JTextField fDateMinInput = new JTextField();
//        fDateMinInput.setBounds(620, 240, 100, 50);
//        frame.add(fDateMinInput);

        JLabel MaxDateLabel = new JLabel("End:");
        MaxDateLabel.setBounds(750, 240, 100, 50);
        frame.add(MaxDateLabel);

        // add date picker with an icon for the button
        DatePicker maxDatePicker = new DatePicker(dateSettings);
        JButton datePickerButton2 = maxDatePicker.getComponentToggleCalendarButton();
        datePickerButton.setText("");
        datePickerButton.setIcon(dateExampleIcon);
        maxDatePicker.setBounds(800,240,100,50);
        frame.add(maxDatePicker);

//        JTextField fDateMaxInput = new JTextField();
//        fDateMaxInput.setBounds(800, 240, 100, 50);
//        frame.add(fDateMaxInput);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(700, 330, 100, 50);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Result<String[][], List<ErrorMsg>> result = scLogic.getFilteredList(
                        fPriceMinInput.getText(),
                        fPriceMaxInput.getText(),
                        fQuantityMinInput.getText(),
                        fQuantityMaxInput.getText(),
                        minDatePicker.getDateStringOrEmptyString(),
                        maxDatePicker.getDateStringOrEmptyString()
                );
                // need to regen table
            }
        });
        frame.add(confirmButton);

        frame.setVisible(true);
    }

    public StockCheckingLogic getscLogic(){
        return scLogic;
    }
    public void regenTable(){
        table.setModel(new DefaultTableModel(scLogic.getProductList(), COLUMNS));
    }

}
