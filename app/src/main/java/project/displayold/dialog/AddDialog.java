package project.displayold.dialog;

import com.github.lgooddatepicker.demo.FullDemo;
import project.displayold.MainDisplay;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

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

        // add date picker with an icon for the button
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.getEnableYearMenu();
        DatePicker datePicker = new DatePicker(dateSettings);
        URL dateImageURL = FullDemo.class.getResource("/images/datepickerbutton1.png");
        Image dateExampleImage = Toolkit.getDefaultToolkit().getImage(dateImageURL);
        ImageIcon dateExampleIcon = new ImageIcon(dateExampleImage);
        JButton datePickerButton = datePicker.getComponentToggleCalendarButton();
        datePickerButton.setText("");
        datePickerButton.setIcon(dateExampleIcon);
        datePicker.setBounds(220,200,200,50);
        add(datePicker);

//        JButton addButton = new JButton("Add");
//        addButton.setBounds(10, 540, 200, 50);
//        addButton.addActionListener(e -> {
//            List<ErrorMsg> errorMsgList = display.getLogic().addProduct(
//                    nameInput.getText(),
//                    quantityInput.getText(),
//                    priceInput.getText(),
//                    datePicker.getDateStringOrEmptyString()
//            );
//            if (errorMsgList.isEmpty()) {
//                display.regenTable();
//                dispose();
//            } else {
//                new ErrorDialog(errorMsgList);
//            }
//        });
//        add(addButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220, 540, 200, 50);
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setVisible(true);
    }

}
