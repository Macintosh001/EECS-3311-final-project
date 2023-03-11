package project.display.views.builders;

import javax.swing.*;

public interface LabelBuilder {

    public JLabel buildLabel(String title, int x, int y,
                             int width, int height);
}
