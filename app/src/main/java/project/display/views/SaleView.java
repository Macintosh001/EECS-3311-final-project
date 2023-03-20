package project.display.views;

import project.display.Display;
import project.display.buttons.BackButton;
import project.display.dialogs.ErrorDialog;
import project.display.dialogs.sale_dialog.ApplyCouponDialog;
import project.display.dialogs.sale_dialog.ScanDialog;
import project.display.views.builders.OtherBuilder;
import project.logic.SaleLogic;
import project.objects.ErrorMsg;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class SaleView extends JPanel implements ViewWithTable {
    private final SaleLogic logic;
    private final String[] COLUMNS = {"Barcode", "Name", "Quantity", "Price"};
    private final OtherBuilder builder = new OtherBuilder();
    private JTable table;
    private JLabel totalPrice;

    public SaleView(Display display, SaleLogic logic) {
        super();
        this.logic = logic;
        setBounds(0, 0, 1000, 700);
        setLayout(null);

        table = builder.buildTable(logic.getCartTable(),COLUMNS);
        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,330,580);
        add(tablePane);

        totalPrice = builder.buildLabel("Total Cost: $0.00");
        totalPrice.setBounds(10, 610, 300, 50);
        add(totalPrice);

        JButton scanButton = builder.buildButton("Scan");
        scanButton.setBounds(350, 10, 200, 50);
        scanButton.addActionListener(e -> new ScanDialog(this));
        add(scanButton);

        JButton applyCouponButton = builder.buildButton("Apply Coupon");
        applyCouponButton.setBounds(350, 70, 200, 50);
        applyCouponButton.addActionListener(e -> new ApplyCouponDialog(this));
        add(applyCouponButton);

        JButton buyButton = builder.buildButton("Buy");
        buyButton.setBounds(350, 130, 200, 50);
        buyButton.addActionListener(e -> {
                List<ErrorMsg> errorMsgList = logic.buy();
                if (!errorMsgList.isEmpty()) {
                    new ErrorDialog(errorMsgList);
                }
                logic.clearShoppingCart();
                regenTable();
        });
        add(buyButton);

        JButton clearButton = builder.buildButton("Clear Cart");
        clearButton.setBounds(350, 190, 200, 50);
        clearButton.addActionListener(e -> {
            logic.clearShoppingCart();
            regenTable();
        });
        add(clearButton);

        BackButton backButton = new BackButton("Back", display);
        add(backButton);

        // Hide when initialized
        setVisible(false);
    }

    /**
     * @return a reference to the SaleLogic object
     */
    public SaleLogic getLogic() {
        return logic;
    }

    /**
     * Responsible for regenerating the table when this table is updated or altered
     */
    public void regenTable() {
        table.setModel(new DefaultTableModel(logic.getCartTable(), COLUMNS));
        totalPrice.setText("Total Cost: $" + logic.getTotal());
    }
}
