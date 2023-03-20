package project.display.views.builders;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.demo.FullDemo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

public class StockManagingBuilder implements Builder{

    /**
     * @param title
     * @return
     */
    @Override
    public JButton buildButton(String title) {
        return new JButton(title);
    }

    /**
     * @param data
     * @return
     */
    public JTable buildTable(String[][] data, String[] columns) {
        JTable table = new JTable(new DefaultTableModel(data,columns));
        table.setEnabled(false);

        return table;
    }

    /**
     * @return
     */
    public JTextField buildTextField() {
        return new JTextField();
    }

    /**
     * @param title
     * @return
     */
    @Override
    public JLabel buildLabel(String title) {
        return new JLabel(title);
    }

    /**
     * @return
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
}
