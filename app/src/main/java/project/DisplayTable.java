package project;

import javax.swing.*;

public class DisplayTable {
    private JTable table1;
    private JPanel panel1;
    private JFrame frame;

    public DisplayTable(){
        frame = new JFrame();
        frame.setTitle("Basic Table");

        // Column Names
        String[] columnNames = { "ID", "Item name", "Price", "Quantity", "Expiry Date" };

        //dummy data to display in the table
        String[][] data = {
                { "001", "OREOS", "$3.99", "75", "01/01/24" },
                { "002", "CHEETOHS", "$2.99", "44", "01/02/28" }};
        // initializing the JTable
        table1 = new JTable(data, columnNames);
        table1.setBounds(30,40,200,300);
        // adding the JTable to the scroll pane
        JScrollPane sp = new JScrollPane(table1);
        frame.add(sp);
        //frame size
        frame.setSize(500,200);
        // make frame visible
        frame.setVisible(true);
    }
    // driver method
    public static void main(String[] args) {

        new DisplayTable();
    }
}
