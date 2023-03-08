package project.display.views;

import project.display.Display;
import project.display.buttons.BackButton;
import project.display.dialogs.coupon_dialog.AddCouponDialog;
import project.display.dialogs.coupon_dialog.RemoveCouponDialog;
import project.logic.CouponManagerLogic;
import project.logic.OrderLogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CouponManagerView extends JPanel implements ViewWithTable {
    private final CouponManagerLogic logic;
    final String[] COLUMNS = {"Barcode", "Discount"};
    private final JTable table;

    public CouponManagerView(Display display, CouponManagerLogic logic) {
        super();
        this.logic = logic;
        setBounds(0, 0, 1000, 700);
        setLayout(null);

        // Add the table to the UI`
        table = new JTable(new DefaultTableModel(logic.getCouponTable(), COLUMNS));
        table.setEnabled(false);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,380,680);
        add(tablePane);

        BackButton backButton = new BackButton("Back", display);
        add(backButton);

        JButton addButton = new JButton("Add Coupon");
        addButton.setBounds(410, 10, 180, 80);
        addButton.addActionListener(e -> new AddCouponDialog(this));
        add(addButton);

        JButton removeButton = new JButton("Remove Coupon");
        removeButton.setBounds(410, 110, 180, 80);
        removeButton.addActionListener(e -> new RemoveCouponDialog(this));
        add(removeButton);

        JButton updateButton = new JButton("Update Coupon");
        updateButton.setBounds(410, 210, 180, 80);
        updateButton.addActionListener(e -> new RemoveCouponDialog(this));
        add(updateButton);

        // Hide when initialized
        setVisible(false);
    }

    public CouponManagerLogic getLogic() {
        return logic;
    }

    public void regenTable() {
        table.setModel(new DefaultTableModel(logic.getCouponTable(), COLUMNS));
    }
}
