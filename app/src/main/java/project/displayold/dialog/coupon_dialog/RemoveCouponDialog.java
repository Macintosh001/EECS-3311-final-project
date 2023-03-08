package project.displayold.dialog.coupon_dialog;
import project.displayold.CouponManagerView;
import project.displayold.dialog.ErrorDialog;
import project.objects.ErrorMsg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
public class RemoveCouponDialog extends JDialog{

    public RemoveCouponDialog(CouponManagerView cpView){

        setTitle("Remove Coupon");
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

        JButton removeButton = new JButton("Remove Coupon");
        removeButton.setBounds(10, 540, 200, 50);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ErrorMsg> errorMsgList = cpView.getCpLogic().removeCoupon(
                        codeInput.getText()
                );
                if(errorMsgList.isEmpty()){
                    cpView.regenTable();
                    dispose();
                } else {
                    new ErrorDialog(errorMsgList);
                }
            }
        });
        add(removeButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220,540,200,50);
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setVisible(true);
    }
}
