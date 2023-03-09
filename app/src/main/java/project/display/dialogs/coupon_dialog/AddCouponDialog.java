package project.display.dialogs.coupon_dialog;

import project.display.dialogs.ErrorDialog;
import project.display.views.CouponManagerView;
import project.objects.ErrorMsg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddCouponDialog extends JDialog {

    /**
     * @param cpView the parent view of this dialog
     */
    public AddCouponDialog(CouponManagerView cpView){

        setTitle("Add Coupon");
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
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ErrorMsg> errorMsgList = cpView.getLogic().addCoupon(
                        codeInput.getText(),
                        percentInput.getText()
                );
                if(errorMsgList.isEmpty()){
                    cpView.regenTable();
                    dispose();
                } else {
                    new ErrorDialog(errorMsgList);
                }
            }
        });
        add(addButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220,540,200,50);
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setVisible(true);
    }
}
