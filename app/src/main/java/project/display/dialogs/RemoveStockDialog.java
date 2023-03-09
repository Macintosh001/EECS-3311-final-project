package project.display.dialogs;

import project.display.views.StockManagingView;
import project.objects.ErrorMsg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RemoveStockDialog extends JDialog {

    /**
     * @param smView the parent view of this dialog
     */
    public RemoveStockDialog(StockManagingView smView){

        setTitle("Remove Product");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);


        JLabel barcodeInputLabel = new JLabel("Barcode:");
        barcodeInputLabel.setBounds(10, 10, 200, 50);
        add(barcodeInputLabel);

        JTextField barcodeInput = new JTextField();
        barcodeInput.setBounds(70, 10, 200, 50);
        add(barcodeInput);

        JButton removeButton = new JButton("Remove");
        removeButton.setBounds(70, 70, 200, 50);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ErrorMsg> errorMsgList = smView.getLogic().removeProduct(barcodeInput.getText());

                if (errorMsgList.isEmpty()) {
                    smView.regenTable();
                    dispose();
                } else {
                    new ErrorDialog(errorMsgList);
                }
            }
        });
        add(removeButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(70, 200, 200, 50);
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setVisible(true);
    }
}
