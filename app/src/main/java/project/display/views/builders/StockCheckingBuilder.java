package project.display.views.builders;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.demo.FullDemo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

public class StockCheckingBuilder implements Builder{

    private final String[] COLUMNS = {"Barcode", "Name", "Quantity", "Price", "Expiry Date"};
    public StockCheckingBuilder(){}

    /**
     * @return a reference to a date picker
     */

    public DatePicker buildDatePicker() {

        URL dateImageURL = FullDemo.class.getResource("/images/datepickerbutton1.png");
        Image dateExampleImage = Toolkit.getDefaultToolkit().getImage(dateImageURL);
        ImageIcon dateExampleIcon = new ImageIcon(dateExampleImage);
        DatePickerSettings dateSettings = new DatePickerSettings();
        DatePicker datePicker = new DatePicker(dateSettings);
        JButton datePickerButton = datePicker.getComponentToggleCalendarButton();
        datePickerButton.setText("");
        datePickerButton.setIcon(dateExampleIcon);
        return datePicker;
    }

    public String[] getColumns(){
        return COLUMNS;
    }

    /**
     * @param title text of the label
     * @return a reference to the label
     */

    public JLabel buildLabel(String title) {
        return new JLabel(title);
    }

    /**
     * @param title
     * @return
     */
    public JButton buildBUtton(String title) {
         return new JButton(title);
    }

    /**
     * @param data 2D Array that stores the data in the table
     * @return a reference to the table
     */
    public JTable buildTable(String[][] data) {
        JTable table = new JTable(new DefaultTableModel(data,COLUMNS));
        table.setEnabled(false);

        return table;
    }
    /**
     * @return a reference to the text field
     */
    public JTextField buildTextField() {
        return new JTextField();
    }

}
