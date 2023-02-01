package project.objects;

import java.util.List;

/**
 * This is an immutable container for a list of products.
 * That means we should not ever modify it!!!
 * You can extract a two-dimentional array from this class for the JTable.
 */
public class ProductList {
    private List<Product> productList;

    public ProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String[][] getTableEntries() {
        String tableEntries =
    }
}
