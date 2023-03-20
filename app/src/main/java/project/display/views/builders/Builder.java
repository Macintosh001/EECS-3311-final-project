package project.display.views.builders;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;

public interface Builder {

    public JButton buildBUtton(String title);
    public JTable buildTable(String[][] data);
    public JTextField buildTextField();
    public JLabel buildLabel(String title);

}
