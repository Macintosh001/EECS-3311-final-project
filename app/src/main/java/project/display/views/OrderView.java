package project.display.views;

import net.miginfocom.swing.MigLayout;
import project.display.Display;
import project.display.buttons.BackButton;
import project.display.dialogs.order_dialog.*;
import project.display.views.builders.OtherBuilder;
import project.logic.OrderLogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OrderView extends JPanel implements ViewWithTable {

    private final OtherBuilder builder = new OtherBuilder();
    private final OrderLogic logic;
    final String[] COLUMNS = {"Name", "Price", "Shelf Life"};
    private final JTable table;

    /**
     * @param display represents the JFrame that contains this JPanel
     * @param logic reference to the Logic object which handles the functionality and data structures
     */
    public OrderView(Display display, OrderLogic logic) {
        super();
        this.logic = logic;
        setBounds(0, 0, 1000, 700);
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel(new MigLayout("insets 25 25 25 25, gap 25px"));

        table = builder.buildTable(logic.getOrderableList(),COLUMNS);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,580,680);
        add(tablePane, BorderLayout.WEST);

        JButton orderButton = builder.buildButton("Order");
        orderButton.addActionListener(e -> new PlaceOrderDialog(this));
        buttonPanel.add(orderButton, "wrap, width :180:, height :80:");

        JButton updatePriceButton = builder.buildButton("Update Price");
        updatePriceButton.addActionListener(e -> new UpdatePriceDialog(this));
        buttonPanel.add(updatePriceButton, "wrap, width :180:, height :80:");

        JButton updateButton = builder.buildButton("Update Shelf Life");
        updateButton.addActionListener(e -> new UpdateShelfLifeDialog(this));
        buttonPanel.add(updateButton, "wrap, width :180:, height :80:");

        JButton addButton = builder.buildButton("Add");
        addButton.addActionListener(e -> new AddOrderDialog(this));
        buttonPanel.add(addButton, "wrap, width :180:, height :80:");

        JButton removeButton = builder.buildButton("Remove");
        removeButton.addActionListener(e -> new RemoveOrderDialog(this));
        buttonPanel.add(removeButton, "wrap, width :180:, height :80:");

        BackButton backButton = new BackButton("Back", display);
        add(backButton);
        add(buttonPanel, BorderLayout.CENTER);

        // Hide when initialized
        setVisible(false);
    }

    /**
     * @return a reference to the OrderLogic object
     */
    public OrderLogic getLogic() {
        return logic;
    }

    /**
     * This method is responsible for regenerating the table tha has been updated
     */
    public void regenTable() {
        table.setModel(new DefaultTableModel(logic.getOrderableList(), COLUMNS));
    }
}
