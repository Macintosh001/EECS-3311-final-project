package project.display;

import com.github.lgooddatepicker.demo.FullDemo;
import project.objects.ErrorMsg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;


public class UpdateDialog extends JDialog {

    public UpdateDialog(MainDisplay display) {

        setTitle("Update Product");
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

        display.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int i = display.getTable().getSelectedRow();
                // create temporary variables to hold product values
            }
        });

        // add date picker with icon as button
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


        JButton updateButton = new JButton("Update");
        updateButton.setBounds(10, 540, 200, 50);
        updateButton.addActionListener(e -> {
            List<ErrorMsg> errorMsgList = display.getLogic().updateProduct(
                    barcodeInput.getText(),
                    nameInput.getText(),
                    quantityInput.getText(),
                    priceInput.getText(),
                    datePicker.getText()
            );
            if (errorMsgList.isEmpty()) {
                display.regenTable();
                dispose();
            } else {
                new ErrorDialog(errorMsgList);
            }
        });

        add(updateButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220, 540, 200, 50);
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setVisible(true);
    }
}
