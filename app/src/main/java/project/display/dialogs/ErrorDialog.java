package project.display.dialogs;

import project.objects.ErrorMsg;

import javax.swing.*;
import java.util.List;

public class ErrorDialog extends JDialog {


    /**
     * @param errorMsgList list of error messages
     */
    public ErrorDialog(List<ErrorMsg> errorMsgList) {
        setTitle("Error!");
        setSize(400 + 2, 500 + 30);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        StringBuilder errorMsg = new StringBuilder();

        for (ErrorMsg msg: errorMsgList) {
            errorMsg.append(msg.getMessage()).append("\n");
        }

        JTextArea label = new JTextArea();
        label.setText(errorMsg.toString());
        label.setBounds(50, 50, 300, 300);
        label.setEditable(false);
        add(label);

        JButton okButton = new JButton("OK");
        okButton.setBounds(100, 400, 200, 50);
        okButton.addActionListener(e -> dispose());
        add(okButton);

        setVisible(true);
    }
}
