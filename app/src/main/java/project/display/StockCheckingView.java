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

        //Filter by label
        JLabel filterPriceLabel = new JLabel("Filter by Price:");
        filterPriceLabel.setBounds(700, 10, 200, 50);
        frame.add(filterPriceLabel);

        JLabel minPriceLabel = new JLabel("Min:");
        minPriceLabel.setBounds(570, 60, 100, 50);
        frame.add(minPriceLabel);

        JTextField fPriceMinInput = new JTextField();
        fPriceMinInput.setBounds(620, 60, 100, 50);
        frame.add(fPriceMinInput);

        JLabel maxPriceLabel = new JLabel("Max:");
        maxPriceLabel.setBounds(750, 60, 100, 50);
        frame.add(maxPriceLabel);

        JTextField fPriceMaxInput = new JTextField();
        fPriceMaxInput.setBounds(800, 60, 100, 50);
        frame.add(fPriceMaxInput);


        // Filter by Quantity
        JLabel filterQuantityLabel = new JLabel("Filter by Quantity:");
        filterQuantityLabel.setBounds(700, 100, 200, 50);
        frame.add(filterQuantityLabel);

        JLabel minQuantityLabel = new JLabel("Min:");
        minQuantityLabel.setBounds(570, 150, 100, 50);
        frame.add(minQuantityLabel);

        JTextField fQuantityMinInput = new JTextField();
        fQuantityMinInput.setBounds(620, 150, 100, 50);
        frame.add(fQuantityMinInput);

        JLabel maxQuantityLabel = new JLabel("Max:");
        maxQuantityLabel.setBounds(750, 150, 100, 50);
        frame.add(maxQuantityLabel);

        JTextField fQuantityMaxInput = new JTextField();
        fQuantityMaxInput.setBounds(800, 150, 100, 50);
        frame.add(fQuantityMaxInput);


        // Filter by Date
        JLabel filterDateLabel = new JLabel("Filter by Expiry Date:");
        filterDateLabel.setBounds(700, 190, 200, 50);
        frame.add(filterDateLabel);

        JLabel minDateLabel = new JLabel("Start:");
        minDateLabel.setBounds(570, 240, 100, 50);
        frame.add(minDateLabel);

        JTextField fDateMinInput = new JTextField();
        fDateMinInput.setBounds(620, 240, 100, 50);
        frame.add(fDateMinInput);

        JLabel MaxDateLabel = new JLabel("End:");
        MaxDateLabel.setBounds(750, 240, 100, 50);
        frame.add(MaxDateLabel);

        JTextField fDateMaxInput = new JTextField();
        fDateMaxInput.setBounds(800, 240, 100, 50);
        frame.add(fDateMaxInput);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(700, 330, 100, 50);
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
