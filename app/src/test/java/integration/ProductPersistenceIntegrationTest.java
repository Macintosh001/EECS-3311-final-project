package integration;

import org.junit.jupiter.api.Test;
import project.objects.FilterProduct;
import project.objects.Product;
import project.persistence.*;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the real database; this class forces predictable behavior by clearing the database
 * before performing operations. This is a type of integration test.
 * Note1 : This class modifies the database
 * Note2 : This class, unlike the application, must have the password "root1234" and the username "root"
 * setup on the database; for this with access to this class, for modification, simply change the
 * values of the fields of this class to be your own username and password if you are unwilling to change these values.
 */
public class ProductPersistenceIntegrationTest {
    //change these values to your own username/password before running.
    private final String password = "root1234";
    private final String username = "root";
    @Test
    void addProductTest() throws SQLException {
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        assertEquals(3, ps.size());
        assertEquals(200, db.getProduct(3).getQuantity());
        assertEquals(2.99F, db.getProduct(4).getPrice());
        assertEquals(0, db.getProduct(5).getName().compareTo("Apples"));

    }
    @Test
    void getProductTest() throws SQLException {
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));

        Product p = db.getProduct(3);
        assertEquals(200, p.getQuantity());
        assertEquals(0.99F, p.getPrice());
        assertEquals(0, p.getName().compareTo("Tuna"));
    }

    @Test
    void getProductListTest() throws SQLException {
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        ArrayList<Integer> quantities = new ArrayList<>();
        for(Product p : ps){
            quantities.add(p.getQuantity());
        }
        assertTrue(quantities.contains(200));
        assertTrue(quantities.contains(100));
        assertTrue(quantities.contains(400));
    }


    @Test
    void getEmptyProductListTest() throws SQLException {
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        assertEquals(0, ps.size());
    }

    @Test
    void getNonexistantProductTest() throws SQLException {
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();

        Product p = db.getProduct(3);
        assertNull(p);
    }


    @Test
    void addMoreProductTest() throws SQLException {
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(4, "Apples", 0, 0f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 40, 1.9f, new Date(3)));
        db.addProduct(new Product(6, "Grapes", 100, 0.99f, new Date(2)));
        db.addProduct(new Product(7, "Sausage", 4, 19f, new Date(3)));
        db.addProduct(new Product(8, "Soup", 10, 299f, new Date(2)));
        db.addProduct(new Product(9, "Grapes", 4000, 1.9f, new Date(3)));
        db.addProduct(new Product(10, "Beef", 1001, 2.9f, new Date(2)));
        db.addProduct(new Product(11, "Oranges", 4001, 0.9f, new Date(3)));
        db.addProduct(new Product(12, "Oranges", 11, 2.999f, new Date(2)));
        db.addProduct(new Product(13, "Sauce", 44, 10.99f, new Date(3)));
        db.addProduct(new Product(14, "Tuna", 99, 20.99f, new Date(2)));
        db.addProduct(new Product(15, "Soup", 101, 11.99f, new Date(3)));
        db.addProduct(new Product(16, "Chips", 107, 20.99f, new Date(2)));
        db.addProduct(new Product(17, "Ramen", 401, 11.99f, new Date(3)));
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        assertEquals(14, ps.size());
    }

    @Test
    void removeProductTest() throws SQLException {
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(4, "Apples", 0, 0f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 40, 1.9f, new Date(3)));
        db.addProduct(new Product(6, "Grapes", 100, 0.99f, new Date(2)));
        db.removeProduct(4);
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        ArrayList<Integer> ints = new ArrayList<>();
        for(Product p : ps){
            ints.add(p.getBarcode());
        }
        assertFalse(ints.contains(4));
    }

    @Test
    void removeNonexistent() throws SQLException {
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(4, "Apples", 0, 0f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 40, 1.9f, new Date(3)));
        db.addProduct(new Product(6, "Grapes", 100, 0.99f, new Date(2)));
        db.removeProduct(1);
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        assertEquals(3, ps.size());
    }

    @Test
    void removeSameProductTwice() throws SQLException {
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(4, "Apples", 0, 0f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 40, 1.9f, new Date(3)));
        db.addProduct(new Product(6, "Grapes", 100, 0.99f, new Date(2)));
        db.removeProduct(4);
        db.removeProduct(4);
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        ArrayList<Integer> ints = new ArrayList<>();
        for(Product p : ps){
            ints.add(p.getBarcode());
        }
        assertFalse(ints.contains(4));
        assertEquals(2, ps.size());
    }

    @Test
    void removeNegativeBarcode() throws SQLException {
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(4, "Apples", 0, 0f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 40, 1.9f, new Date(3)));
        db.addProduct(new Product(6, "Grapes", 100, 0.99f, new Date(2)));
        db.removeProduct(-1);
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        assertEquals(3, ps.size());
    }

    @Test
    void replaceProductNewQuantityAndPriceTest() throws SQLException {
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(4, "Apples", 0, 0f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 40, 1.9f, new Date(3)));
        db.addProduct(new Product(6, "Grapes", 100, 0.99f, new Date(2)));
        Product apples = new Product(4, "Apples", 2, 1f, new Date(1));
        db.replaceProduct(apples);
        Product p = db.getProduct(4);
        assertEquals(2, p.getQuantity());
        assertEquals(1f, p.getPrice());
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        assertEquals(3, ps.size());
    }

    @Test
    void replaceProductNewNameTest() throws SQLException {
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(4, "Apples", 0, 0f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 40, 1.9f, new Date(3)));
        db.addProduct(new Product(6, "Grapes", 100, 0.99f, new Date(2)));
        Product green = new Product(4, "Green", 0, 0F, new Date(1));
        db.replaceProduct(green);
        Product p = db.getProduct(4);
        assertEquals(0, p.getName().compareTo("Green"));
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        assertEquals(3, ps.size());
    }

    @Test
    void replaceNonexistantProduct() throws SQLException {
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        Product oreos = new Product(3, "oreos", 80, 3.0F, new Date(1));
        db.replaceProduct(oreos);
        Product p = db.getProduct(3);
        assertEquals(0, p.getName().compareTo("oreos"));
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        assertEquals(1, ps.size());
    }

    @Test
    void priceFilter() throws SQLException {
        FilterProduct filt = FilterProduct.FilterProductFactory("price", 2F, 5F);
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        filters.add(filt);
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(1, ps.size());
    }

    @Test
    void quantityFilter() throws SQLException {
        FilterProduct filt = FilterProduct.FilterProductFactory("quantity", 70, 250);
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        filters.add(filt);
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(2, ps.size());
    }

    @Test
    void barcodeFilter() throws SQLException {
        FilterProduct filt = FilterProduct.FilterProductFactory("barcode", 0, 3);
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        filters.add(filt);
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(1, ps.size());
    }

    @Test
    void dateFilter() throws SQLException {
        FilterProduct filt = FilterProduct.FilterProductFactory("expiryDate", new Date(0), new Date(3));
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        filters.add(filt);
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(3, ps.size());
    }


    @Test
    void twoFilters() throws SQLException {
        FilterProduct filt = FilterProduct.FilterProductFactory("price", 0F, 2F);
        FilterProduct filt2 = FilterProduct.FilterProductFactory("quantity", 150, 250);
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        filters.add(filt);
        filters.add(filt2);
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(1, ps.size());
    }

    @Test
    void threeFilters() throws SQLException {
        FilterProduct filt = FilterProduct.FilterProductFactory("expiryDate", new Date(1), new Date(3));
        FilterProduct filt2 = FilterProduct.FilterProductFactory("quantity", 70, 300);
        FilterProduct filt3 = FilterProduct.FilterProductFactory("price", 1F, 3F);
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(4)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        filters.add(filt);
        filters.add(filt2);
        filters.add(filt3);
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(1, ps.size());
    }
    @Test
    void filterAboveStartRangeTest() throws SQLException {
        FilterProduct filt = FilterProduct.FilterProductFactory("quantity", 150, null);

        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        filters.add(filt);
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(2, ps.size());
    }

    @Test
    void filterBelowEndRangeTest() throws SQLException {
        FilterProduct filt = FilterProduct.FilterProductFactory("quantity", null, 100);

        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        filters.add(filt);
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(1, ps.size());
    }

    @Test
    void filterWithNoFilters() throws SQLException {
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(3, ps.size());
    }

    @Test
    void filtersWithAndWithoutNulls() throws SQLException {
        FilterProduct filt = FilterProduct.FilterProductFactory("expiryDate", new Date(1), new Date(3));
        FilterProduct filt2 = FilterProduct.FilterProductFactory("quantity", 200, null);
        FilterProduct filt3 = FilterProduct.FilterProductFactory("price", null, 2.0F);
        ProductDatabase db = new ProductPersistence(username, password);
        db.empty();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        filters.add(filt);
        filters.add(filt2);
        filters.add(filt3);
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(2, ps.size());
    }
}
