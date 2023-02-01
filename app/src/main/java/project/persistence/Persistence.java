package project.persistence;

import project.objects.Filter;
import project.objects.Product;
import project.objects.ProductList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Persistence {
    private final List<Product> productList;

    public Persistence() {
        this.productList = new ArrayList<>();
    }

    public void addProduct(Product product) {
        // TODO implement
    }

    public void removeProduct(Integer barcode) {
        // TODO implement
    }

    public void replaceProduct(Product product) {
        // TODO implement
    }

    public Product getProduct(Integer barcode) {
        // TODO implement
        return null;
    }

    public ProductList getProductList() {
        return null;
    }

    public ProductList getFilteredProductList(Filter filter) {
        // TODO implement
        return null;
    }
}
