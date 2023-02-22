package project.logic;

import project.objects.Product;

import java.util.List;

public class TableEntryGenerator {
    public static String[][] genTableEntries(List<Product> list) {
        String[][] tableEntries = new String[list.size()][5];

        int i = 0;
        for (Product product: list) {
            tableEntries[i][0] = product.getBarcode().toString();
            tableEntries[i][1] = product.getName();
            tableEntries[i][2] = product.getQuantity().toString();
            tableEntries[i][3] = String.format("%.2f", product.getPrice());
            tableEntries[i][4] = product.getExpityDate().toString();
            i++;
        }

        return tableEntries;
    }
}
