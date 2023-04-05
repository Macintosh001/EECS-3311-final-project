package project.display.views;

import net.miginfocom.swing.MigLayout;
import project.display.Display;
import project.display.buttons.BackButton;
import project.display.dialogs.automation_dialog.AddRestockDialog;
import project.display.dialogs.automation_dialog.RemoveRestockDialog;
import project.display.dialogs.automation_dialog.UpdateRestockDialog;
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
        addButton.addActionListener(e -> new AddRestockDialog(this));
        controlPanel.add(addButton, "split 3, width :200:, height :50:, gapafter 10px");

        JButton removeButton = builder.buildButton("Remove Restock Task");
        removeButton.addActionListener(e -> new RemoveRestockDialog(this));
        controlPanel.add(removeButton, "width :200:, height :50:, gapafter 10px");

        JButton updateButton = builder.buildButton("Update Restock Task");
        updateButton.addActionListener(e -> new UpdateRestockDialog(this));
        controlPanel.add(updateButton, "width :200:, height :50:");

        BackButton backButton = new BackButton("Back", display);
        add(backButton);
        add(controlPanel, BorderLayout.CENTER);

        setVisible(false);
    }

    public AutomationLogic getLogic(){
        return logic;
    }

    public void regenTable(){
        table.setModel(new DefaultTableModel(logic.getRestockTaskTable(), COLUMNS));
    }
}
