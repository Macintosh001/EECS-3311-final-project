package project.display.views;

import project.display.Display;
import project.display.views.builders.Builder;
import project.display.views.builders.StockCheckingBuilder;
import project.logic.StockCheckingLogic;

import javax.swing.*;
import java.awt.*;

public class StockCheck extends JPanel {

    private Builder builder;

    public StockCheck(Display display, StockCheckingLogic logic){

        super();
        JPanel buttonPanel = new JPanel();
        display.setLayout(new BorderLayout());
        builder = new StockCheckingBuilder();

        setBounds(0,0,1000,700);
        setLayout(new GridLayout(1,2));

        JTable table = builder.buildTable(logic.getProductList());

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,480,680);
        add(tablePane, "West");

        JLabel filterPriceLabel = builder.buildLabel("Filter by Price:");

        JLabel minPriceLabel = builder.buildLabel("Min:");

        JTextField fPriceMinInput = builder.buildTextField();

    }
}
