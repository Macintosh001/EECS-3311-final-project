package project.display.views.builders;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.demo.FullDemo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

public class StockManagingBuilder implements Builder{

    private final String[] COLUMNS = {"Barcode", "Name", "Quantity", "Price", "Expiry Date"};
    private int labelX_offset = 0;
    private int labelY_offset = 0;
    private int buttonX_offset = 0;
    private int buttonY_offset = 0;
    private int buttonCount = 0;
    private int labelCount = 0;

    /**
     * @param title
     * @return
     */
    @Override
    public JButton buildBUtton(String title) {
        return new JButton(title);
    }

    /**
     * @param data
     * @return
     */
    public JTable buildTable(String[][] data) {
        JTable table = new JTable(new DefaultTableModel(data,COLUMNS));
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

    public void setButtonX_offset(int x){
        buttonX_offset = x;
    }

    public void setButtonY_offset(int y){
        buttonY_offset = y;
    }

    public void setLabelX_offset(int x){
        labelX_offset = x;
    }

    public void setLabelY_offset(int y){
        labelY_offset = y;
    }

    public String[] getColumns(){
        return this.COLUMNS;
    }
}
