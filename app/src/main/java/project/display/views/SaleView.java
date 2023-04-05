package project.display.views;

import net.miginfocom.swing.MigLayout;
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
import java.awt.*;
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
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel(new MigLayout("insets 25, gap 25px"));

        table = builder.buildTable(logic.getCartTable(),COLUMNS);
        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,330,580);
        add(tablePane, BorderLayout.WEST);


        JButton scanButton = builder.buildButton("Scan");
        scanButton.addActionListener(e -> new ScanDialog(this));
        buttonPanel.add(scanButton, "wrap, width :200:, height :50:");

        JButton applyCouponButton = builder.buildButton("Apply Coupon");
        applyCouponButton.addActionListener(e -> new ApplyCouponDialog(this));
        buttonPanel.add(applyCouponButton, "wrap, width :200:, height :50:");

        JButton buyButton = builder.buildButton("Buy");
        buyButton.addActionListener(e -> {
                List<ErrorMsg> errorMsgList = logic.buy();
                if (!errorMsgList.isEmpty()) {
                    new ErrorDialog(errorMsgList);
                }
                logic.clearShoppingCart();
                regenTable();
        });
        buttonPanel.add(buyButton, "wrap, width :200:, height :50:");

        JButton clearButton = builder.buildButton("Clear Cart");
        clearButton.addActionListener(e -> {
            logic.clearShoppingCart();
            regenTable();
        });
        buttonPanel.add(clearButton, "wrap, width :200:, height :50:");

        totalPrice = builder.buildLabel("Total Cost: $0.00");
        totalPrice.setBounds(10, 610, 300, 50);
        buttonPanel.add(totalPrice, "cell 0 18, width :300:, height :50:");

        BackButton backButton = new BackButton("Back", display);
        add(backButton);
        add(buttonPanel, BorderLayout.CENTER);

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
