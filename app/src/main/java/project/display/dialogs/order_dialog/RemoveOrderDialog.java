package project.display.dialogs.order_dialog;

import project.display.dialogs.ErrorDialog;
import project.display.views.OrderView;
import project.objects.ErrorMsg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RemoveOrderDialog extends JDialog {

    /**
     * @param oView the parent view of this dialog
     */
    public RemoveOrderDialog(OrderView oView){

        setTitle("Remove Order");
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

        JButton removeOrderButton = new JButton("Remove Order");
        removeOrderButton.setBounds(10, 540, 200, 50);
        removeOrderButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                List<ErrorMsg> errorMsgList = oView.getLogic().removeOrderable(
                        nameInput.getText()
                );
                if(errorMsgList.isEmpty()){
                    oView.regenTable();
                    dispose();
                } else {
                    new ErrorDialog(errorMsgList);
                }
            }
        });
        add(removeOrderButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220,540,200,50);
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setVisible(true);
    }
}
