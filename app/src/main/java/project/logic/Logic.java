package project.logic;

import project.objects.Product;
import project.objects.ProductList;
import project.persistence.Persistence;

import java.util.Date;

public class Logic implements ILogic {
    Persistence persistence;

    private static int nextBarcode = 0;

    public void addProduct(String name, Integer quantity, Float price, Date expiryDate) {
        Product product = new Product(Logic.nextBarcode, name, quantity, price, expiryDate);
        Logic.nextBarcode++;
        this.persistence.addProduct(product);
    }

    public void removeProduct(Integer barcode) {
        this.persistence.removeProduct(barcode);
    }

    public void updateProductName(Integer barcode, String name) {
        Product oldProduct = this.persistence.getProduct(barcode);
        Product newProduct = new Product(barcode, name, oldProduct.getQuantity(), oldProduct.getPrice(), oldProduct.getExpityDate());
        this.persistence.replaceProduct(newProduct);
    }

    public void updateProductQuantity(Integer barcode, Integer quantity){
        Product oldProduct = this.persistence.getProduct(barcode);
        Product newProduct = new Product(barcode, oldProduct.getName(), quantity, oldProduct.getPrice(), oldProduct.getExpityDate());
        this.persistence.replaceProduct(newProduct);
    }

    public void updateProductPrice(Integer barcode, Float price) {
        Product oldProduct = this.persistence.getProduct(barcode);
        Product newProduct = new Product(barcode, oldProduct.getName(), oldProduct.getQuantity(), price, oldProduct.getExpityDate());
        this.persistence.replaceProduct(newProduct);
    }

    public void updateProductExpiryDate(Integer barcode, Date expityDate) {
        Product oldProduct = this.persistence.getProduct(barcode);
        Product newProduct = new Product(barcode, oldProduct.getName(), oldProduct.getQuantity(), oldProduct.getPrice(), expityDate);
        this.persistence.replaceProduct(newProduct);
    }

    public ProductList getProductList() {
        return this.persistence.getProductList();
    }
}
