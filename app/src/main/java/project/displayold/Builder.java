package project.displayold;

import javax.swing.*;

public interface Builder {

    public JButton buildButton(String title);
    public JFrame buildFrame(String title);
    public JTextField buildField();
    public JLabel buildLabel(String label);

}