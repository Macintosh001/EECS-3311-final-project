package project.display.dialog;

import project.display.StockManagingView;
import project.objects.ErrorMsg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RemoveStockDialog extends JDialog {

    public RemoveStockDialog(StockManagingView smView){

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
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ErrorMsg> errorMsgList = smView.getsmLogic().removeProduct(barcodeInput.getText());
            }
        });
    }
}
