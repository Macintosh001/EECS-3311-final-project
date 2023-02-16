package project.logic;

import project.objects.ErrorMsg;
import project.objects.ProductList;
import java.util.List;

public interface ILogic {
    List<ErrorMsg> addProduct(String name, String quantity, String price, String expiryDate);
    List<ErrorMsg> removeProduct(String barcode);
    List<ErrorMsg> updateProduct(String barcode, String name, String quantity, String price, String expiryDate);
    ProductList getProductList();
}
