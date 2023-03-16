package project.display.views;

import project.display.Display;
import project.display.views.builders.Director;
import project.display.views.builders.StockCheckingBuilder;
import project.logic.StockCheckingLogic;

import javax.swing.*;
import java.awt.*;

public class StockCheck extends JPanel {

    private final String[] COLUMNS = {"Barcode", "Name", "Quantity", "Price", "Expiry Date"};
    private Director director;

    public StockCheck(Display display, StockCheckingLogic logic){

        super();
        StockCheckingBuilder stockBuilder = new StockCheckingBuilder();
        this.director = new Director(stockBuilder);

        setBounds(0,0,1000,700);
        setLayout(new GridLayout(1,2));

        JTable table = director.getBuilder().
                buildTable(logic.getProductList(),
                        COLUMNS);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,480,680);
        add(tablePane);

        JLabel filterPriceLabel = director.getBuilder()
                .buildLabel("Filter by Price:", 700,10,200,50);

        JLabel minPriceLabel = director.getBuilder()
                .buildLabel("Min:", 570,60,100,50);

        JTextField fPriceMinInput = director.getBuilder()
                .buildTextField( 620,60,100,50);

    }
}
