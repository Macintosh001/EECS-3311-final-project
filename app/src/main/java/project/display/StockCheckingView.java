package project.display;

import project.logic.StockCheckingLogic;
import project.objects.ErrorMsg;
import project.objects.Result;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StockCheckingView {

    private StockCheckingLogic scLogic = null;
    private JTable table;
    private final String[] COLUMNS = {"Barcode", "Name", "Price", "Quantity", "Expiry Date"};

    public StockCheckingView(StockCheckingLogic scLogic){

        this.scLogic = scLogic;

        JFrame frame = new JFrame("Stock Checking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1050,720);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        table = new JTable(new DefaultTableModel(scLogic.getProductList(), COLUMNS));
        table.setEnabled(false);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,500,400);
        frame.add(tablePane);

        JLabel filterPriceLabel = new JLabel("Filter by Price:");
        filterPriceLabel.setBounds(670, 10, 200, 50);
        frame.add(filterPriceLabel);

        JTextField fPriceMinInput = new JTextField();
        fPriceMinInput.setBounds(620, 60, 100, 50);
        frame.add(fPriceMinInput);

        JTextField fPriceMaxInput = new JTextField();
        fPriceMaxInput.setBounds(730, 60, 100, 50);
        frame.add(fPriceMaxInput);


        JLabel filterQuantityLabel = new JLabel("Filter by Quantity:");
        filterQuantityLabel.setBounds(670, 100, 200, 50);
        frame.add(filterQuantityLabel);

        JTextField fQuantityMinInput = new JTextField();
        fQuantityMinInput.setBounds(620, 150, 100, 50);
        frame.add(fQuantityMinInput);

        JTextField fQuantityMaxInput = new JTextField();
        fQuantityMaxInput.setBounds(730, 150, 100, 50);
        frame.add(fQuantityMaxInput);

        JLabel filterDateLabel = new JLabel("Filter by Expiry Date:");
        filterDateLabel.setBounds(670, 190, 200, 50);
        frame.add(filterDateLabel);

        JTextField fDateMinInput = new JTextField();
        fDateMinInput.setBounds(620, 240, 100, 50);
        frame.add(fDateMinInput);

        JTextField fDateMaxInput = new JTextField();
        fDateMaxInput.setBounds(730, 240, 100, 50);
        frame.add(fDateMaxInput);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(10, 540, 100, 50);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Result<String[][], List<ErrorMsg>> result = null;
            }
        });
        frame.add(confirmButton);

        frame.setVisible(true);
    }

    public StockCheckingLogic getscLogic(){
        return scLogic;
    }
    public void regenTable(){
        table.setModel(new DefaultTableModel(scLogic.getProductList(), COLUMNS));
    }

}
