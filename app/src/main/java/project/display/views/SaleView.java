package project.display.views;

import project.display.Display;
import project.display.buttons.BackButton;
import project.display.dialogs.sale_dialog.ApplyCouponDialog;
import project.display.dialogs.sale_dialog.ScanDialog;
import project.logic.SaleLogic;
import project.objects.ErrorMsg;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class SaleView extends JPanel implements ViewWithTable {
    private final SaleLogic logic;
    private final String[] COLUMNS = {"Name", "Price"};
    private JTable table;

    public SaleView(Display display, SaleLogic logic) {
        super();
        this.logic = logic;
        setBounds(0, 0, 1000, 700);
        setLayout(null);

        table = new JTable(new DefaultTableModel(logic.getCartTable(), COLUMNS));
        table.setEnabled(false);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,330,680);
        add(tablePane);

        JButton scanButton = new JButton("Scan");
        scanButton.setBounds(350, 10, 200, 50);
        scanButton.addActionListener(e -> new ScanDialog(this));
        add(scanButton);

        JButton applyCouponButton = new JButton("Apply Coupon");
        applyCouponButton.setBounds(350, 70, 200, 50);
        applyCouponButton.addActionListener(e -> new ApplyCouponDialog(this));
        add(applyCouponButton);

        JButton buyButton = new JButton("Buy");
        buyButton.setBounds(350, 130, 200, 50);
        buyButton.addActionListener(e -> {
                List<ErrorMsg> errorMsgList = logic.buy();
        });
        add(buyButton);

        JButton clearButton = new JButton("Clear Cart");
        clearButton.setBounds(350, 190, 200, 50);
        clearButton.addActionListener(e -> logic.clearShoppingCart());
        add(clearButton);

        BackButton backButton = new BackButton("Back", display);
        add(backButton);

        // Hide when initialized
        setVisible(false);
    }

    public SaleLogic getLogic() {
        return logic;
    }

    public void regenTable() {
        table.setModel(new DefaultTableModel(logic.getCartTable(), COLUMNS));
    }
}
