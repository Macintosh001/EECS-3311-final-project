package project.persistence;

import project.objects.FilterProduct;
import project.objects.Product;

import java.util.List;

/**
 * The database interface contains operations for interacting with a database.
 * It is meant to be implemented by a class that handles database operations and a
 * database stub for testing purposes. This way, business logic can communicate with the interface
 * while not knowing if it is communicating with a stub or true database.
 */
public interface ProductDatabase {


    //Query DB or stub

    /**
     * Gets a product from the db/stub with a given barcode
     * @param barcode: the barcode of a product
     * @return a product with the barcode.
     */
    public Product getProduct(Integer barcode);

    /**
     * @return a productList representing the objects of a db/stub
     */
    public List<Product> getProductList();


    //Modification of DB or stub

    /**
     * Add a product to db or stub
     * @param product : the product added
     */
    public void addProduct(Product product);

    /**
     * Search for and remove a product from the db or stub
     * @param barcode: the barcode of the removed product
     */
    public void removeProduct(Integer barcode);

    /**
     * Replace a product in the db/stub with an updated version of itself.
     * @param product: a product
     */
    public void replaceProduct(Product product);

    /**
     *  apply a list of filters to the products in storage; output the products that
     *  meet the criteria of all filter objects in the list of filters.
     * @param filters a list of filter objects
     * @return a filtered list of products
     */
    public List<Product> getFilteredProductList(List<FilterProduct> filters);

    public void empty();
}
