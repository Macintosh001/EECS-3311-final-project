package project.persistence;

import project.objects.FilterProduct;
import project.objects.Product;
import project.objects.ProductList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        try{
            Statement statement = db.exportStatement();
            ResultSet res = statement.executeQuery("select * from product where barcode =" + barcode.toString());
            Product p = extractProductFromResultSet(res);
            db.terminate();
            return p;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public ProductList getProductList() {
        ArrayList<Product> prod = new ArrayList<>();

        try{
            Statement statement = db.exportStatement();
            ResultSet res = statement.executeQuery("select barcode from product");
            if(res != null){
                Product p;
                while(res.next()){
                    p = getProduct(res.getInt("barcode"));
                    prod.add(p);
                }
            }
            db.terminate();
        }catch(SQLException e){
            e.printStackTrace();
        }

        ProductList query = new ProductList(prod);
        return query;
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
                "'" + name + "'" + ", " +
                price + ", " +
                quantity + ", " +
                "'"+expiryDate + "'"+ ")";
        return ret;
    }

    private Product extractProductFromResultSet(ResultSet res){
        Integer barcode;
        String name;
        Float price;
        Integer quantity;
        Date expiryDate;

        try{
            if(res != null){
                res.next();
                barcode = res.getInt("barcode");
                name = res.getString("name");
                price = res.getFloat("price");
                quantity = res.getInt("quantity");
                expiryDate = new Date(res.getDate("expiry_date").getTime());
                return new Product(barcode, name, quantity, price, expiryDate);
            }
            else {
                return null;
            }
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProductList getFilteredProductList(List<FilterProduct> filters){
        String filterString = this.getFilterString(filters);
        try{
            Statement statement = db.exportStatement();
            ResultSet res = statement.executeQuery(filterString);
            ProductList productList = this.extractProductListFromResultSet(res);
            db.terminate();
            return productList;
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    private ProductList extractProductListFromResultSet(ResultSet res){
        ArrayList<Product> prodList= new ArrayList<>();
        try{
            if(res != null){
                while(res.next()){
                    Product p = this.getProduct(res.getInt("barcode"));
                    prodList.add(p);
                }
                ProductList ret = new ProductList(prodList);
                return ret;
            }
            return null;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public String getFilterString(List<FilterProduct> filters){
        StringBuilder filterString = new StringBuilder("select barcode from Product where ");
        int count = 0;
        for(FilterProduct filter: filters){
            String rangeStart;
            String rangeEnd;
            String filterType;
            if(filter.getRangeStart() == null && filter.getRangeEnd() != null){

                if(filter.getRangeEnd().getClass().getName().toLowerCase().compareTo("java.util.date") == 0){
                    java.util.Date date = (java.util.Date) filter.getRangeEnd();
                    java.sql.Date dateEnd = new java.sql.Date(date.getTime());
                    rangeEnd = dateEnd.toString();
                }
                else{
                    rangeEnd = filter.getRangeEnd().toString();
                }

                if(filter.getFilterType().toLowerCase().compareTo("expirydate") == 0){
                    filterType = "expiry_date";
                }
                else{
                    filterType = filter.getFilterType();
                }

                if(count == 0){
                    filterString.append(filterType).append(" <= ").append("'").append(rangeEnd).append("'");
                }
                else {
                    filterString.append(" AND ").append(filterType).append(" <= ").append("'").append(rangeEnd).append("'");
                }
                count++;
            }
            else if(filter.getRangeEnd() == null && filter.getRangeStart() != null){
                if(filter.getRangeStart().getClass().getName().toLowerCase().compareTo("java.util.date") == 0){
                    java.util.Date date = (java.util.Date) filter.getRangeStart();
                    java.sql.Date dateStart = new java.sql.Date(date.getTime());
                    rangeStart = dateStart.toString();
                }
                else{
                    rangeStart = filter.getRangeStart().toString();
                }
                if(filter.getFilterType().toLowerCase().compareTo("expirydate") == 0){
                    filterType = "expiry_date";
                }
                else{
                    filterType = filter.getFilterType();
                }

                if(count == 0){
                    filterString.append(filterType).append(" >= ").append("'").append(rangeStart).append("'");
                }
                else {
                    filterString.append(" AND ").append(filterType).append(" >= ").append("'").append(rangeStart).append("'");
                }
                count++;
            }
            else if(filter.getRangeEnd() != null && filter.getRangeStart() != null){
                if(filter.getRangeStart().getClass().getName().toLowerCase().compareTo("java.util.date") == 0){
                    java.util.Date date = (java.util.Date) filter.getRangeStart();
                    java.sql.Date dateStart = new java.sql.Date(date.getTime());
                    rangeStart = dateStart.toString();
                }
                else{
                    rangeStart = filter.getRangeStart().toString();
                }

                if(filter.getRangeEnd().getClass().getName().toLowerCase().compareTo("java.util.date") == 0){
                    java.util.Date date = (java.util.Date) filter.getRangeEnd();
                    java.sql.Date dateEnd = new java.sql.Date(date.getTime());
                    rangeEnd = dateEnd.toString();
                }
                else{
                    rangeEnd = filter.getRangeEnd().toString();
                }

                if(filter.getFilterType().toLowerCase().compareTo("expirydate") == 0){
                    filterType = "expiry_date";
                }
                else{
                    filterType = filter.getFilterType();
                }

                if(count == 0){
                    filterString.append(filterType).append(" between ").append("'").append(rangeStart).append("'").append(" and ").append("'").append(rangeEnd).append("'");
                }
                else {
                    filterString.append(" AND ").append(filterType).append(" between ").append("'").append(rangeStart).append("'").append(" and ").append("'").append(rangeEnd).append("'");
                }
                count++;
            }


        } //end for
        return filterString.toString();
    }

}
