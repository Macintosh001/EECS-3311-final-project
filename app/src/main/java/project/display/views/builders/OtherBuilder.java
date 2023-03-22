package project.display.views.builders;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OtherBuilder implements Builder{


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
    @Override
    public JTable buildTable(String[][] data, String[] columns) {
        JTable table = new JTable(new DefaultTableModel(data, columns));
        table.setEnabled(false);
        return table;
    }

    /**
     * @return
     */
    @Override
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
}
