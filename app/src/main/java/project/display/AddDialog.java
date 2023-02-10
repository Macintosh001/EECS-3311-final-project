package project.display;

import project.objects.ErrorMsg;

import javax.swing.*;
import java.util.List;

public class AddDialog extends JDialog {

    public AddDialog(MainDisplay display) {
        setTitle("Add Product");
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

        JLabel priceInputLabel = new JLabel("Price: ");
        priceInputLabel.setBounds(10, 130, 200, 50);
        add(priceInputLabel);

        JTextField priceInput = new JTextField();
        priceInput.setBounds(220, 130, 200, 50);
        add(priceInput);

        JLabel dateInputLabel = new JLabel("Expiry Date:");
        dateInputLabel.setBounds(10, 200, 200, 50);
        add(dateInputLabel);

        JTextField dateInput = new JTextField();
        dateInput.setBounds(220, 200, 200, 50);
        add(dateInput);

        JLabel dateHintLabel = new JLabel("Date Format: yyyy-mm-dd");
        dateHintLabel.setBounds(10, 260, 200, 50);
        add(dateHintLabel);

        JButton addButton = new JButton("Add");
        addButton.setBounds(10, 540, 200, 50);
        addButton.addActionListener(e -> {
            List<ErrorMsg> errorMsgList = display.getLogic().addProduct(
                    nameInput.getText(),
                    quantityInput.getText(),
                    priceInput.getText(),
                    dateInput.getText()
            );
            if (errorMsgList.isEmpty()) {
                display.regenTable();
                dispose();
            } else {
                new ErrorDialog(errorMsgList);
            }
        });
        add(addButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220, 540, 200, 50);
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setVisible(true);
    }

}
