package project.display.views;

import project.display.Display;
import project.display.buttons.BackButton;
import project.logic.OrderLogic;
import project.logic.SaleLogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
        tablePane.setBounds(10,10,380,680);
        add(tablePane);

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
