package project.display.views.builders;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;

public interface Builder {

    public JButton buildButton(String title);
    public JTable buildTable(String[][] data, String[] columns);
    public JTextField buildTextField();
    public JLabel buildLabel(String title);

}
