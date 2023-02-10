package project.logic;

import project.objects.Product;
import project.objects.ProductList;
import project.persistence.Database;

import java.util.Date;

public class Logic implements ILogic {
    Database database;

    private static int nextBarcode = 0;

    public Logic(Database database, int nextBarcode) {
        this.database = database;
        Logic.nextBarcode = nextBarcode;
    }

    public void addProduct(String name, Integer quantity, Float price, Date expiryDate) {
        if (hasItemWithName(name)) {
            System.out.println("A product with name " + name + " already exists");
            return;
        }

        Product product = new Product(Logic.nextBarcode, name, quantity, price, expiryDate);
        Logic.nextBarcode++;
        this.database.addProduct(product);
}

    public void removeProduct(Integer barcode) {
        this.database.removeProduct(barcode);
    }

    public void updateProductName(Integer barcode, String name) {
        Product oldProduct = this.database.getProduct(barcode);
        Product newProduct = new Product(barcode, name, oldProduct.getQuantity(), oldProduct.getPrice(), oldProduct.getExpityDate());
        this.database.replaceProduct(newProduct);
    }

    public void updateProductQuantity(Integer barcode, Integer quantity){
        Product oldProduct = this.database.getProduct(barcode);
        Product newProduct = new Product(barcode, oldProduct.getName(), quantity, oldProduct.getPrice(), oldProduct.getExpityDate());
        this.database.replaceProduct(newProduct);
    }

    public void updateProductPrice(Integer barcode, Float price) {
        Product oldProduct = this.database.getProduct(barcode);
        Product newProduct = new Product(barcode, oldProduct.getName(), oldProduct.getQuantity(), price, oldProduct.getExpityDate());
        this.database.replaceProduct(newProduct);
    }

    public void updateProductExpiryDate(Integer barcode, Date expityDate) {
        Product oldProduct = this.database.getProduct(barcode);
        Product newProduct = new Product(barcode, oldProduct.getName(), oldProduct.getQuantity(), oldProduct.getPrice(), expityDate);
        this.database.replaceProduct(newProduct);
    }

    public ProductList getProductList() {
        return this.database.getProductList();
    }

    private boolean hasItemWithName(String name) {
        ProductList productList = this.database.getProductList();
        for (String[] product : productList.getTableEntries()) {
            if (product[1].equals(name)) {
                return true;
            }
        }
        return false;
  }
}
