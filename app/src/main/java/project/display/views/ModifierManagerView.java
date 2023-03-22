package project.display.views;

import com.github.lgooddatepicker.components.DatePicker;
import net.miginfocom.swing.MigLayout;
import project.display.Display;
import project.display.buttons.BackButton;
import project.display.dialogs.ErrorDialog;
import project.display.views.builders.OtherBuilder;
import project.display.views.builders.StockManagingBuilder;
import project.logic.ModifierManagerLogic;
import project.objects.ErrorMsg;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifierManagerView extends JPanel implements ViewWithTable{

    private final ModifierManagerLogic logic;
    private final StockManagingBuilder builder = new StockManagingBuilder();
    private final String[] COLUMNS = {"Name", "Modifier", "Start Date", "End Date"};
    private final JTable table;

    public ModifierManagerView(Display display, ModifierManagerLogic logic){
        super();
        this.logic = logic;
        setBounds(0,0,1000,700);
        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel(new MigLayout("insets 25, gap 25px"));

        table = builder.buildTable(logic.getModifierTable(), COLUMNS);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(10,10,580,680);
        add(tablePane, BorderLayout.WEST);


        JLabel nameLabel = builder.buildLabel("Name");
        JTextField nameField = builder.buildTextField();
        JLabel modLabel = builder.buildLabel("Modifier:");
        JTextField modField = builder.buildTextField();
        JLabel startDateLabel = builder.buildLabel("Start Date:");
        DatePicker startDate = builder.buildDatePicker();
        JLabel endDateLabel = builder.buildLabel("End Date:");
        DatePicker endDate = builder.buildDatePicker();
        JButton addButton = builder.buildButton("Add Modifier");
        addButton.addActionListener(e -> {
            List<ErrorMsg> errorMsgList = logic.addModifier(
                            nameField.getText(),
                            modField.getText(),
                            startDate.getDateStringOrEmptyString(),
                            endDate.getDateStringOrEmptyString()
            );
            if(errorMsgList.isEmpty()){
                this.regenTable();
            } else {
                new ErrorDialog(errorMsgList);
            }
        });
        JButton removeButton = builder.buildButton("Remove Modifier");
        removeButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter name:");
            List<ErrorMsg> errorMsgList = logic.removeModifier(name);
            if(errorMsgList.isEmpty()){
                this.regenTable();
            } else {
                new ErrorDialog(errorMsgList);
            }
        });
        BackButton backButton = new BackButton("Back", display);

        controlPanel.add(nameLabel, "split 4, sg a");
        controlPanel.add(nameField, "width :200:, height :30:");
        controlPanel.add(modLabel, "sg a");
        controlPanel.add(modField, "wrap, width :200:, height :30:");
        controlPanel.add(startDateLabel, "split 4, sg a");
        controlPanel.add(startDate);
        controlPanel.add(endDateLabel, "sg a");
        controlPanel.add(endDate, "wrap");
        controlPanel.add(addButton, "split 2, width :180:, height :50:, gapafter 15px");
        controlPanel.add(removeButton, "width :180:, height :50:");
        add(backButton);
        add(controlPanel, BorderLayout.CENTER);

        setVisible(false);
    }

    public ModifierManagerLogic getLogic(){
        return logic;
    }
    /**
     *
     */
    @Override
    public void regenTable() {
        table.setModel(new DefaultTableModel(logic.getModifierTable(), COLUMNS));
    }
}
