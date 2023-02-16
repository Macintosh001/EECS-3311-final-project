package project.persistence;

import project.objects.Product;
import project.objects.ProductList;
import java.sql.*;

public class Persistence implements Database{
    private DatabaseManager db;

    public Persistence(){
        this.db = new DatabaseManager();
        if(!db.databaseExists()) {
            db.createDB();
        }
    }

    @Override
    public Product getProduct(Integer barcode) {
        return null;
    }

    @Override
    public ProductList getProductList() {
        return null;
    }

    @Override
    public void addProduct(Product product) {
        String values = this.getSQLProductString(product);
        try {
            Statement statement = this.db.exportStatement();
            statement.execute("insert into product values " + values);
            this.db.terminate();
        } catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void removeProduct(Integer barcode) {
        try {
            Statement statement = this.db.exportStatement();
            statement.execute("delete from product where barcode ="+ barcode.toString());
            this.db.terminate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void replaceProduct(Product product) {
        removeProduct(product.getBarcode());
        addProduct(product);
    }

    private String getSQLProductString(Product product){
        String barcode = product.getBarcode().toString();
        String quantity = product.getQuantity().toString();
        String name = product.getName();
        String price = product.getPrice().toString();

        java.sql.Date expDate = new java.sql.Date(product.getExpityDate().getTime());
        String expiryDate = expDate.toString();

        String ret = "(" +
                barcode + ", " +
                "\'" + name + "\'" + ", " +
                price + ", " +
                quantity + ", " +
                "\'"+expiryDate + "\'"+ ")";
        return ret;
    }
}
