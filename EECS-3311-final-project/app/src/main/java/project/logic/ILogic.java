package project.logic;

import project.objects.ProductList;

import java.util.Date;

public interface ILogic {
    public void addProduct(String name, Integer quantity, Float price, Date expiryDate);
    public void removeProduct(Integer barcode);
    public void updateProductName(Integer barcode, String name);
    public void updateProductQuantity(Integer barcode, Integer quantity);
    public void updateProductPrice(Integer barcode, Float price);
    public void updateProductExpiryDate(Integer barcode, Date expityDate);
    public ProductList getProductList();
}
