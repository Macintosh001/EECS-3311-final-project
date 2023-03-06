package project.display.dialog.order_dialog;

import project.display.OrderView;
import project.display.dialog.ErrorDialog;
import project.objects.ErrorMsg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PlaceOrderDialog extends JDialog{

    public PlaceOrderDialog(OrderView oView){

        setTitle("Place Order");
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

        JLabel quantityInputLabel = new JLabel("Quantity:");
        quantityInputLabel.setBounds(10, 70, 200, 50);
        add(quantityInputLabel);

        JTextField quantityInput = new JTextField();
        quantityInput.setBounds(220, 70, 200, 50);
        add(quantityInput);

        JButton orderButton = new JButton("Order");
        orderButton.setBounds(10, 540, 200, 50);
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ErrorMsg> errorMsgList = oView.getOLogic().placeOrder(
                       nameInput.getText(),
                       quantityInput.getText()
                );
                if(errorMsgList.isEmpty()){
                    oView.regenTable();
                    dispose();
                } else {
                    new ErrorDialog(errorMsgList);
                }
            }
        });
        add(orderButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220,540,200,50);
        //cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setVisible(true);

    }
}
