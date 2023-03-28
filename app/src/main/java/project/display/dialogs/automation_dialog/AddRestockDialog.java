package project.display.dialogs.automation_dialog;

import net.miginfocom.swing.MigLayout;
import project.display.dialogs.ErrorDialog;
import project.display.views.AutomationView;
import project.display.views.builders.OtherBuilder;
import project.objects.ErrorMsg;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AddRestockDialog extends JDialog {

    OtherBuilder builder = new OtherBuilder();
    public AddRestockDialog(AutomationView view){

        setTitle("Add Restock Task");
        setSize(800 + 2, 600 + 30);
        setLocationRelativeTo(null);
        setLayout(new MigLayout("insets 20, gap 20px"));
        setResizable(false);

        JLabel nameLabel = builder.buildLabel("Name:");
        JLabel quantityLabel = builder.buildLabel("Minimum Quantity:");
        JLabel amountLabel = builder.buildLabel("Restock Amount:");

        JTextField nameField = builder.buildTextField();
        JTextField quantityField = builder.buildTextField();
        JTextField amountField = builder.buildTextField();

        add(nameLabel, "split 2, sg a");
        add(nameField, "wrap, width :180:, height :30:");
        add(quantityLabel, "split 2, sg a");
        add(quantityField, "wrap, width :180:, height :30:");
        add(amountLabel, "split 2, sg a");
        add(amountField, "wrap, width :180:, height :30:");

        JButton addButton = builder.buildButton("Add Restock Task");
        addButton.addActionListener(e -> {
            List<ErrorMsg> errorMsgs = view.getLogic().addRestockTask(
                    nameField.getText(),
                    quantityField.getText(),
                    amountField.getText()
            );
            if(errorMsgs.isEmpty()){
                view.regenTable();
                dispose();
            } else {
                new ErrorDialog(errorMsgs);
            }
        });
        add(addButton, "split 2, width :200:, height :50:, gapafter 10px");

        JButton cancelButton = builder.buildButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton, "width :200:, height :50:");

        setVisible(true);
    }
}
