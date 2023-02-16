package project.objects;

import java.util.List;

/**
 * This is an immutable container for a list of products.
 * That means we should not ever modify it!!!
 * You can extract a two-dimensional array from this class for the JTable.
 */
public class ProductList {
    private final List<Product> productList;

    public ProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public String[][] getTableEntries() {
        String[][] tableEntries = new String[this.productList.size()][5];

        int i = 0;
        for (Product product: this.productList) {
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
