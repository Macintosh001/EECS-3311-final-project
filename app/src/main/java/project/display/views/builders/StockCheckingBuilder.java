package project.display.views.builders;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.demo.FullDemo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

public class StockCheckingBuilder implements ButtonBuilder, TableBuilder, LabelBuilder, TextFieldBuilder, DatePickerBuilder{

    public StockCheckingBuilder(){}
    /**
     * @param title the name of the button
     * @param x horizontal position
     * @param y vertical position
     * @param width width of the button
     * @param height height of the button
     * @return a reference to a JButton
     */
    @Override
    public JButton buildBUtton(String title, int x, int y, int width, int height) {
        JButton button = new JButton(title);
        button.setBounds(x,y,width,height);

        return button;
    }

    /**
     * @param x horizontal position
     * @param y vertical position
     * @param width width of the date picker
     * @param height height of the date picker
     * @return a reference to a date picker
     */
    @Override
    public DatePicker buildDatePicker(int x, int y, int width, int height) {

        URL dateImageURL = FullDemo.class.getResource("/images/datepickerbutton1.png");
        Image dateExampleImage = Toolkit.getDefaultToolkit().getImage(dateImageURL);
        ImageIcon dateExampleIcon = new ImageIcon(dateExampleImage);

        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.getEnableYearMenu();
        DatePicker datePicker = new DatePicker(dateSettings);
        JButton datePickerButton = datePicker.getComponentToggleCalendarButton();
        datePickerButton.setText("");
        datePickerButton.setIcon(dateExampleIcon);
        datePicker.setBounds(x,y,width,height);

        return datePicker;
    }

    /**
     * @param title text of the label
     * @param x horizontal position
     * @param y vertical position
     * @param width width of the label
     * @param height height of the label
     * @return a reference to the label
     */
    @Override
    public JLabel buildLabel(String title, int x, int y, int width, int height) {
        JLabel label = new JLabel(title);
        label.setBounds(x,y,width,height);

        return label;
    }

    /**
     * @param data 2D Array that stores the data in the table
     * @param columns top header row of the table
     * @param x horizontal position
     * @param y vertical position
     * @param width width of the table
     * @param height height of the table
     * @return a reference to the table
     */
    @Override
    public JTable buildTable(String[][] data, String[] columns, int x, int y, int width, int height) {
        JTable table = new JTable(new DefaultTableModel(data,columns));
        table.setEnabled(false);

        return table;
    }

    /**
     * @param x horizontal position
     * @param y vertical position
     * @param width width of the text field
     * @param height height of the text field
     * @return a reference to the text field
     */
    @Override
    public JTextField buildTextField(int x, int y, int width, int height) {
        return null;
    }
}
