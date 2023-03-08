package project.display.dialogs.order_dialog;

import project.display.dialogs.ErrorDialog;
import project.display.views.OrderView;
import project.objects.ErrorMsg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UpdateShelfLifeDialog extends JDialog {

    public UpdateShelfLifeDialog(OrderView oView){

        setTitle("Update Shelf Life");
        setSize(800 + 2, 600 + 30);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        JLabel nameInputLabel = new JLabel("Name:");
        nameInputLabel.setBounds(10, 10, 200, 50);
        add(nameInputLabel);

        JTextField nameInput = new JTextField();
        nameInput.setBounds(220, 10, 200, 50);
        add(nameInput);

        JLabel shelfLifeInputLabel = new JLabel("Shelf Life:");
        shelfLifeInputLabel.setBounds(10, 70, 200, 50);
        add(shelfLifeInputLabel);

        JTextField shelfLifeInput = new JTextField();
        shelfLifeInput.setBounds(220, 70, 200, 50);
        add(shelfLifeInput);

        JButton updateButton = new JButton("Update Shelf Life");
        updateButton.setBounds(10, 540, 200, 50);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ErrorMsg> errorMsgList = oView.getLogic().updateShelfLife(
                        nameInput.getText(),
                        shelfLifeInput.getText()
                );
                if(errorMsgList.isEmpty()){
                    oView.regenTable();
                    dispose();
                } else {
                    new ErrorDialog(errorMsgList);
                }
            }
        });

        add(updateButton);


        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220,540,200,50);
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setVisible(true);
    }
}
