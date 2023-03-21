package project.display.views;

import net.miginfocom.swing.MigLayout;
import project.display.Display;
import project.display.buttons.BackButton;
import project.display.dialogs.coupon_dialog.AddCouponDialog;
import project.display.dialogs.coupon_dialog.RemoveCouponDialog;
import project.display.dialogs.coupon_dialog.UpdateCouponDialog;
import project.display.views.builders.OtherBuilder;
import project.logic.CouponManagerLogic;
import project.logic.OrderLogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CouponManagerView extends JPanel implements ViewWithTable {

    private final OtherBuilder builder = new OtherBuilder();
    private final CouponManagerLogic logic;
    final String[] COLUMNS = {"Barcode", "Discount"};
    private final JTable table;

    /**
     * @param display represents the JFrame that contains this JPanel
     * @param logic reference to the Logic object which handles the functionality and data structures
     */
    public CouponManagerView(Display display, CouponManagerLogic logic) {
        super();
        this.logic = logic;
        setBounds(0, 0, 1000, 700);
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel(new MigLayout("insets 25 25 25 25, gap 25px"));

        table = builder.buildTable(logic.getCouponTable(), COLUMNS);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,380,680);
        add(tablePane, BorderLayout.WEST);

        BackButton backButton = new BackButton("Back", display);
        add(backButton);

        JButton addButton = builder.buildButton("Add Coupon");
        addButton.addActionListener(e -> new AddCouponDialog(this));
        buttonPanel.add(addButton, "wrap, width :180:, height :80:");

        JButton removeButton = builder.buildButton("Remove Coupon");
        removeButton.addActionListener(e -> new RemoveCouponDialog(this));
        buttonPanel.add(removeButton, "wrap, width :180:, height :80:");

        JButton updateButton = builder.buildButton("Update Coupon");
        updateButton.addActionListener(e -> new UpdateCouponDialog(this));
        buttonPanel.add(updateButton, "wrap, width :180:, height :80:");

        add(buttonPanel, BorderLayout.CENTER);

        // Hide when initialized
        setVisible(false);
    }

    /**
     * @return a reference to the CouponManagerLogic object
     */
    public CouponManagerLogic getLogic() {
        return logic;
    }

    /**
     * Responsible for regenerating a table when that table has been updated or altered
     */
    public void regenTable() {
        table.setModel(new DefaultTableModel(logic.getCouponTable(), COLUMNS));
    }
}
