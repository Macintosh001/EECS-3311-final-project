package project.persistence;

import project.objects.FilterProduct;
import project.objects.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to interact with the product table of the mySQL database.
 * Storage is persistent. Interact with this class through the interface ProductDatabase.
 */
public class ProductPersistence implements ProductDatabase {
    private DatabaseManager db;

   /**
    * Constructor: initialize fields.
    * Check if a mySQL database exists; if not, build one on the user's local host server with the
    * databaseManager field. Force the current version of the database
     * for those with missing tables.
    * This way, a user is guaranteed to have a database that supports the operations in this class
     * before these operations are invoked; this design avoids errors of missing tables or databases.
            */
    public ProductPersistence(String username, String password) throws SQLException{
        this.db = new DatabaseManager(username, password);

        //test connection, throw exception if fails
        this.db.connectToServer();
        this.db.terminate();

        //build database if it doesn't exist
        if(!db.databaseExists()) {
            db.createDB();
        }
        db.forceCurrentVersion();
    }

    /**
     * Retrieve a product with the given barcode from the database. Build and return a product object
     * that is equivalent to the retrieved database entity.
     * Database unchanged by this method
     * @param barcode barcode of the retrieved coupon
     * @return a product object representing the product entity in the database.
     */
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

    /**
     * Returns a list of products representing the current content of the product table in the database
     * @return a list of products equivalent to the data in the product table.
     */
    @Override
    public List<Product> getProductList() {
        ArrayList<Product> prod = new ArrayList<>();

        try{
            Statement statement = db.exportStatement();
            ResultSet res = statement.executeQuery("select barcode from product");
            prod = this.extractProductListFromResultSet(res);
            db.terminate();
        }catch(SQLException e){
            e.printStackTrace();
        }

        return prod;
    }

    /**
     * Add product to the product table of the database by first converting the
     * product object to an SQL appropriate string, then executing SQL commands with this string
     * @param product to be added
     */
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

    /**
     * Delete a product with the given barcode from the coupon table in the database.
     * @param barcode a barcode of the product to be removed
     */
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

    /**
     * Update a product in storage by deleting it from the product table and adding
     *  a new product with the same code but updated information to the table.
     *   If no product with the same barcode as the input product exists in storage, a new product will be added to storage.
     *  @param product updated product object
     */
    @Override
    public void replaceProduct(Product product) {
        removeProduct(product.getBarcode());
        addProduct(product);
    }

    /**
     * Convert product object into a string of its field values in a form
     * useful for JDBC SQL statements.
     * Used to convert a product into a form in which it is addable to the product table of the database.
     * Helper method for addProduct()
     * @param product a product object
     * @return a string of the field values for the product, that is addable to the product table in the database
     */
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

    /**
     *  Takes a ResultSet object that was created from querying a single product entity from the coupon table,
     *  and converts this result set into a product object that stores equivalent information.
     *  This is useful in the getProduct method, which will return the result of its call to this method.
     * @param res a resultSet representing a single product having been queried from the product table
     * @return a product object representing the result of querying a single row of the product table.
     */
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

    /**
     * Return the list of products left over after a list of filters is applied.
     * That is, for each filter, if the product has a value between the rangeStart and RangeEnd values pf the filter,
     * or above rangeStart if rangeEnd is null, or below end if start is null, then
     * the product is not removed from the list by a given filter.
     * Works by generating a single SQL command equivalent to all filters in the filter list,
     * then applying this command
     * @return the leftover products after all filters have been applied.
     */
    @Override
    public List<Product> getFilteredProductList(List<FilterProduct> filters){
        //generate an sql string from all filters
        String filterString = this.getFilterString(filters);
        try{
            Statement statement = db.exportStatement();
            ResultSet res = statement.executeQuery(filterString);
            ArrayList<Product> productList = this.extractProductListFromResultSet(res);
            db.terminate();
            return productList;
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * given a resultset representing a query from the product table,
     * this method uses the getProduct method to create a set of product objects
     * that represent a set or subset of products currently in the database.
     * This is useful in the getProductList method, which returns the result of its call to this method
     * @param res a resultSet representing a list of queried values from the product table
     * @return a list of product objects that are currently present in the database
     *
     */
    private ArrayList<Product> extractProductListFromResultSet(ResultSet res){
        ArrayList<Product> prodList= new ArrayList<>();
        try{
            if(res != null){
                while(res.next()){
                    Product p = this.getProduct(res.getInt("barcode"));
                    prodList.add(p);
                }
                return prodList;
            }
            return null;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Given a list of filters, create a sql query that captures the same information
     * @param filters a list of filters
     * @return a string representing a sql query equivalent to all filters in the list
     */
    private String getFilterString(List<FilterProduct> filters){
        //starting generic code
        StringBuilder filterString = new StringBuilder("select barcode from product where ");
        int count = 0;
        for(FilterProduct filter: filters){
            //string to be added to the query
            String rangeStart;
            String rangeEnd;
            String filterType;
            //case 1: empty range end, query above rangestart
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
            } //case 2: rangestart empty, query below rangeend
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
            } //case 3: query inside a range
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

                //use between start and end in sql to filter
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


    @Override
    public void empty(){
        try{
            Statement statement = db.exportStatement();
            statement.execute("delete from product");
            db.terminate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
