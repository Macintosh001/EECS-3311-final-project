package project.logic;

import project.objects.Product;
import project.persistence.Persistence;

import java.util.Date;

public class MutatorLogic {
    Persistence persistence;

    public void addProduct(String name, Integer quantity, Float price, Date expiryDate) {
        Product product = new Product(name, quantity, price, expiryDate);
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
}
