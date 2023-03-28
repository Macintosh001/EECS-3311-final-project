package project.display.views;

import net.miginfocom.swing.MigLayout;
import project.display.Display;
import project.display.buttons.BackButton;
import project.display.views.builders.OtherBuilder;
import project.logic.AutomationLogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AutomationView extends JPanel {

    private final String[] COLUMNS = {"Name", "Minimum Quantity", "Restock Amount"};
    private OtherBuilder builder;
    private AutomationLogic logic;
    private JTable table;

    public AutomationView(Display display, AutomationLogic alogic){

        super();
        builder = new OtherBuilder();
        this.logic = alogic;
        setBounds(0,0,1000,700);
        setLayout(new BorderLayout());

        table = builder.buildTable(logic.getRestockTaskTable(), COLUMNS);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,480,680);
        add(tablePane, BorderLayout.WEST);

        JPanel controlPanel = new JPanel(new MigLayout("insets 20, gap 30px"));

        JButton addButton = builder.buildButton("Add Restock Task");
        controlPanel.add(addButton);

        JButton removeButton = builder.buildButton("Remove Restock Task");
        controlPanel.add(removeButton);

        JButton autoButton = builder.buildButton("Automate");
        controlPanel.add(autoButton);

        BackButton backButton = new BackButton("Back", display);
        add(backButton);
        add(controlPanel, BorderLayout.CENTER);

        setVisible(false);
    }

    public void regenTable(){
        table.setModel(new DefaultTableModel(logic.getRestockTaskTable(), COLUMNS));
    }
}
