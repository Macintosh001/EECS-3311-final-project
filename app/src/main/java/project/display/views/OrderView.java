package project.display.views;

import project.display.Display;
import project.display.buttons.BackButton;
import project.display.dialogs.order_dialog.*;
import project.logic.OrderLogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OrderView extends JPanel implements ViewWithTable {
    private final OrderLogic logic;
    final String[] COLUMNS = {"Name", "Price", "Shelf Life"};
    private JTable table;

    /**
     * @param display represents the JFrame that contains this JPanel
     * @param logic reference to the Logic object which handles the functionality and data structures
     */
    public OrderView(Display display, OrderLogic logic) {
        super();
        this.logic = logic;
        setBounds(0, 0, 1000, 700);
        setLayout(null);

        // Add all UI elements
        table = new JTable(new DefaultTableModel(logic.getOrderableList(), COLUMNS));
        table.setEnabled(false);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,580,680);
        add(tablePane);

        // This is the button to add products
        JButton orderButton = new JButton("Order");
        orderButton.setBounds(610, 10, 180, 80);
        orderButton.addActionListener(e -> new PlaceOrderDialog(this));
        add(orderButton);

        // This is the button to update price
        JButton updatePriceButton = new JButton("Update Price");
        updatePriceButton.setBounds(610, 110, 180, 80);
        updatePriceButton.addActionListener(e -> new UpdatePriceDialog(this));
        add(updatePriceButton);

        // This is the button to update shelf life
        JButton updateButton = new JButton("Update Shelf Life");
        updateButton.setBounds(610, 210, 180, 80);
        updateButton.addActionListener(e -> new UpdateShelfLifeDialog(this));
        add(updateButton);

        JButton addButton = new JButton("Add");
        addButton.setBounds(610, 310, 180, 80);
        addButton.addActionListener(e -> new AddOrderDialog(this));
        add(addButton);

        JButton removeButton = new JButton("Remove");
        removeButton.setBounds(610, 410, 180, 80);
        removeButton.addActionListener(e -> new RemoveOrderDialog(this));
        add(removeButton);


        BackButton backButton = new BackButton("Back", display);
        add(backButton);

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
