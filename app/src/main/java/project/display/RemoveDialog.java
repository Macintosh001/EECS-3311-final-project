package project.display;

import project.objects.ErrorMsg;

import javax.swing.*;
import java.util.List;

public class RemoveDialog extends JDialog {
    public RemoveDialog(MainDisplay display) {
        setTitle("Remove Product");
        setSize(800 + 2, 600 + 30);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        JLabel barcodeInputLabel = new JLabel("Barcode:");
        barcodeInputLabel.setBounds(10, 10, 200, 50);
        add(barcodeInputLabel);

        JTextField barcodeInput = new JTextField();
        barcodeInput.setBounds(220, 10, 200, 50);
        add(barcodeInput);

        JButton removeButton = new JButton("Remove");
        removeButton.setBounds(10, 540, 200, 50);
        removeButton.addActionListener(e -> {
            List<ErrorMsg> errorMsgList = display.getLogic().removeProduct(barcodeInput.getText());
            if (errorMsgList.isEmpty()) {
                display.regenTable();
                dispose();
            } else {
                new ErrorDialog(errorMsgList);
            }
        });
        add(removeButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220, 540, 200, 50);
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setVisible(true);
    }
}
