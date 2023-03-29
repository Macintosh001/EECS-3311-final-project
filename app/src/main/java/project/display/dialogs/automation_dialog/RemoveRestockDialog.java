package project.display.dialogs.automation_dialog;

import net.miginfocom.swing.MigLayout;
import project.display.dialogs.ErrorDialog;
import project.display.views.AutomationView;
import project.display.views.builders.OtherBuilder;
import project.objects.ErrorMsg;

import javax.swing.*;
import java.util.List;

public class RemoveRestockDialog extends JDialog {

    OtherBuilder builder = new OtherBuilder();

    public RemoveRestockDialog(AutomationView view){

        setTitle("Remove Restock Task");
        setSize(800 + 2, 600 + 30);
        setLocationRelativeTo(null);
        setLayout(new MigLayout("insets 20, gap 20px"));
        setResizable(false);

        JLabel nameLabel = builder.buildLabel("Name:");
        JTextField nameField = builder.buildTextField();
        add(nameLabel, "split 2");
        add(nameField, "wrap, width :180:, height :30:");


        JButton removeButton = builder.buildButton("Remove Restock Task");
        removeButton.addActionListener(e -> {
            List<ErrorMsg> errorMsgs = view.getLogic().removeRestockTask(
                    nameField.getText()
            );
            if(errorMsgs.isEmpty()){
                view.regenTable();
                dispose();
            } else {
                new ErrorDialog(errorMsgs);
            }
        });
        add(removeButton, "split 2, width :200:, height :50:, gapafter 10px");

        JButton cancelButton = builder.buildButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton, "width :200:, height :50:");

        setVisible(true);
    }
}
