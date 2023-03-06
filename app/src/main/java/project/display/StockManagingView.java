package project.display;

import project.logic.StockManagingLogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StockManagingView {

    private StockManagingLogic smLogic = null;
    private JTable table;
    private final String[] COLUMNS = {"Barcode", "Name", "Price", "Quantity", "Expiry Date"};
    public StockManagingView(StockManagingLogic smLogic){

        this.smLogic = smLogic;




        table = new JTable(new DefaultTableModel(smLogic.getProductList(), COLUMNS));
        table.setEnabled(false);
    }
}
