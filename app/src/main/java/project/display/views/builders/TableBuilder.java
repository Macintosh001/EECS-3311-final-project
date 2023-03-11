package project.display.views.builders;

import javax.swing.*;

public interface TableBuilder {

    public JTable buildTable(String[][] data, String[] columns, int x,
                             int y, int width, int height);
}
