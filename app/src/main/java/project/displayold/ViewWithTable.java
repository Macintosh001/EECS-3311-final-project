package project.displayold;

import javax.swing.*;

public class ViewWithTable implements Builder {

    private final int y_bound = 10;
    private final int width = 1050;
    private final int height = 720;

    private int y_offset = 60;
    private double x_offset = 0.78d;

    public ViewWithTable(){}

    public JButton buildButton(String title) {

        JButton button = new JButton(title);
        button.setBounds((int) (width * x_offset), 10 + y_offset, (int)(width * 0.19), (int)(height * 0.07));
        return button;
    }
    public JFrame buildFrame(String title) {

        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        return frame;
    }
    public JLabel buildLabel(String label) {

        JLabel lbl = new JLabel(label);
        lbl.setBounds(20,20,80, 30);

        return lbl;
    }
    public JTextField buildField() {
        return null;
    }
    public void setY_offset(int y_offset) {
        this.y_offset = y_offset;
    }

    public JTable buildTable(){

        JTable table = new JTable();

        return null;
    }
}