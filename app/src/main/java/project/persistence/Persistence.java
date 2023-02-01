package project.persistence;

import project.objects.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Persistence {
    private final List<Product> productList;

    public Persistence() {
        this.productList = new ArrayList<>();
    }

    public void addProduct(Product product) {
        this.productList.add(product);
    }

    public void removeProduct(Integer barcode) {
        for (Product product: this.productList) {
            if (Objects.equals(product.getBarcode(), barcode)) {
                this.productList.remove(product);
            }
        }
    }

    public void replaceProduct(Product product) {
        this.removeProduct(product.getBarcode());
        this.addProduct(product);
    }

    public Product getProduct(Integer barcode) {
        // TODO implement
        return null;
    }
}
