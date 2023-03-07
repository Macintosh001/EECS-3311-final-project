package project.display.dialog.sale_dialog;

import project.display.MainDisplay;
import project.display.SaleView;
import project.display.dialog.ErrorDialog;
import project.objects.ErrorMsg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ApplyCouponDialog extends JDialog {

    public ApplyCouponDialog(SaleView sView){

        setTitle("Apply Coupon");
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

        JButton applyButton = new JButton("Apply Coupon");
        applyButton.setBounds(10, 540, 200, 50);
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ErrorMsg> errorMsgList = sView.getsLogic().applyCoupon(codeInput.getText());
                if (errorMsgList.isEmpty()) {
                    sView.regenTable();
                    dispose();
                } else {
                    new ErrorDialog(errorMsgList);
                }
            }
        });
        add(applyButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220, 540, 200, 50);
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setVisible(true);
    }

}
