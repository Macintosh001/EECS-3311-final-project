package project.display;

import project.display.dialog.ErrorDialog;
import project.objects.ErrorMsg;

import javax.swing.*;
import java.util.List;

public interface Builder {

    public JButton buildButton(String title);
    public JFrame buildFrame(String title);
    public JTextField buildField();
    public JLabel buildLabel(String label);

}