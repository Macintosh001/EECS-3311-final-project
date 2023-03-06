package project.display.dialog.order_dialog;

import project.display.OrderView;
import project.display.dialog.ErrorDialog;
import project.logic.OrderLogic;
import project.objects.ErrorMsg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddOrderDialog extends JDialog {

    public AddOrderDialog(OrderView oView){

        setTitle("Add Order");
        setSize(800 + 2, 600 + 30);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        JLabel codeInputLabel = new JLabel("Code:");
        codeInputLabel.setBounds(10, 10, 200, 50);
        add(codeInputLabel);

        JTextField codeInput = new JTextField();
        codeInput.setBounds(220, 10, 200, 50);
        add(codeInput);

        JLabel percentInputLabel = new JLabel("Percentage off:");
        percentInputLabel.setBounds(10, 70, 200, 50);
        add(percentInputLabel);

        JTextField percentInput = new JTextField();
        percentInput.setBounds(220, 70, 200, 50);
        add(percentInput);

        JButton addButton = new JButton("Add Coupon");
        addButton.setBounds(10, 540, 200, 50);
        //addButton.addActionListener(new ActionListener() {

            //public void actionPerformed(ActionEvent e) {
//                List<ErrorMsg> errorMsgList = oView.getCpLogic().addCoupon(
//                        codeInput.getText(),
//                        percentInput.getText()
//                );
//                if(errorMsgList.isEmpty()){
//                    oView.regenTable();
//                    dispose();
//                } else {
//                    new ErrorDialog(errorMsgList);
//                }
//            }
                // });
                // add(addButton


        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220,540,200,50);
        //cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setVisible(true);

    }
}
