package unit;
import org.junit.jupiter.api.Test;
import project.objects.FilterProduct;
import project.objects.Product;
import project.persistence.ProductDatabase;
import project.persistence.ProductDatabaseStub;

import java.util.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//note: logic handles most extreme input values and error inputs, so it is not expected that
//values passed to the database stub classes will be incorrect during calls.
public class ProductDatabaseStubTest {

    @Test
    void getProductTest(){
        ProductDatabase db = new ProductDatabaseStub();
        Product p = db.getProduct(0);
        assertEquals(75, p.getQuantity());
        assertEquals(3.99F, p.getPrice());
        assertEquals(0, p.getName().compareTo("oreo"));
    }

    @Test
    void getNonexistantProductTest(){
        ProductDatabase db = new ProductDatabaseStub();
        Product p = db.getProduct(3);
        assertNull(p);
    }

    @Test
    void dateAddGetTest(){
        ProductDatabase db = new ProductDatabaseStub();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        Product p = db.getProduct(3);
        assertEquals(0, p.getExpityDate().compareTo(new Date(1)));
    }



    @Test
    void getProductListTest(){
        ProductDatabase db = new ProductDatabaseStub();
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        ArrayList<Integer> quantities = new ArrayList<>();
        for(Product p : ps){
            quantities.add(p.getQuantity());
        }
        assertTrue(quantities.contains(75));
        assertTrue(quantities.contains(44));
    }

    @Test
    void getEmptyProductListTest(){
        ProductDatabase db = new ProductDatabaseStub();
        db.empty();
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        assertEquals(0, ps.size());
    }

    @Test
    void addProductTest(){
        ProductDatabase db = new ProductDatabaseStub();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        assertEquals(5, ps.size());
        assertEquals(200, db.getProduct(3).getQuantity());
        assertEquals(2.99F, db.getProduct(4).getPrice());
        assertEquals(0, db.getProduct(5).getName().compareTo("Apples"));
    }

    @Test
    void addNullProductTest(){
        ProductDatabase db = new ProductDatabaseStub();
        db.addProduct(null);
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        assertEquals(5, ps.size());
    }

    @Test
    void addMoreProductTest(){
        ProductDatabase db = new ProductDatabaseStub();
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
        assertEquals(16, ps.size());
    }



    @Test
    void removeProductTest(){
        ProductDatabase db = new ProductDatabaseStub();
        db.removeProduct(0);
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        ArrayList<Integer> ints = new ArrayList<>();
        for(Product p : ps){
            ints.add(p.getBarcode());
        }
        assertFalse(ints.contains(0));
    }


    @Test
    void removeNonexistent(){
        ProductDatabase db = new ProductDatabaseStub();
        db.removeProduct(3);
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        assertEquals(2, ps.size());
    }

    @Test
    void removeSameProductTwice(){
        ProductDatabase db = new ProductDatabaseStub();
        db.removeProduct(0);
        db.removeProduct(0);
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        ArrayList<Integer> ints = new ArrayList<>();
        for(Product p : ps){
            ints.add(p.getBarcode());
        }
        assertFalse(ints.contains(0));
        assertEquals(1, ps.size());
    }

    @Test
    void removeNegativeBarcode(){
        ProductDatabase db = new ProductDatabaseStub();
        db.removeProduct(-1);
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        assertEquals(2, ps.size());
    }

    @Test
    void replaceProductNewDateTest(){
        ProductDatabase db = new ProductDatabaseStub();
        Product oreos = new Product(0, "oreos", 75, 3.99F, new Date(1));
        db.replaceProduct(oreos);
        Product p = db.getProduct(0);
        assertEquals(0, p.getExpityDate().compareTo(new Date(1)));
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        assertEquals(2, ps.size());
    }

    @Test
    void replaceProductNewQuantityAndPriceTest(){
        ProductDatabase db = new ProductDatabaseStub();
        Product oreos = new Product(0, "oreos", 80, 3.0F, new Date());
        db.replaceProduct(oreos);
        Product p = db.getProduct(0);
        assertEquals(80, p.getQuantity());
        assertEquals(3.0F, p.getPrice());
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        assertEquals(2, ps.size());
    }

    @Test
    void replaceProductNewNameTest(){
        ProductDatabase db = new ProductDatabaseStub();
        Product green = new Product(0, "Green", 75, 3.99F, new Date());
        db.replaceProduct(green);
        Product p = db.getProduct(0);
        assertEquals(0, p.getName().compareTo("Green"));
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        assertEquals(2, ps.size());
    }

    @Test
    void replaceNonexistantProduct(){
        ProductDatabase db = new ProductDatabaseStub();
        Product oreos = new Product(3, "oreo", 80, 3.0F, new Date(1));
        db.replaceProduct(oreos);
        Product p = db.getProduct(3);
        assertEquals(0, p.getName().compareTo("oreo"));
        ArrayList<Product> ps = (ArrayList<Product>) db.getProductList();
        assertEquals(3, ps.size());
    }

    @Test
    void priceFilter(){
        FilterProduct filt = FilterProduct.FilterProductFactory("price", 2F, 5F);
        ProductDatabase db = new ProductDatabaseStub();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        filters.add(filt);
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(3, ps.size());
    }

    @Test
    void quantityFilter(){
        FilterProduct filt = FilterProduct.FilterProductFactory("quantity", 70, 250);
        ProductDatabase db = new ProductDatabaseStub();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        filters.add(filt);
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(3, ps.size());
    }

    @Test
    void barcodeFilter(){
        FilterProduct filt = FilterProduct.FilterProductFactory("barcode", 0, 3);
        ProductDatabase db = new ProductDatabaseStub();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        filters.add(filt);
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(3, ps.size());
    }

    @Test
    void dateFilter(){
        FilterProduct filt = FilterProduct.FilterProductFactory("expiryDate", new Date(1), new Date(3));
        ProductDatabase db = new ProductDatabaseStub();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        filters.add(filt);
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(3, ps.size());
    }


    @Test
    void twoFilters(){
        FilterProduct filt = FilterProduct.FilterProductFactory("expiryDate", new Date(1), new Date(3));
        FilterProduct filt2 = FilterProduct.FilterProductFactory("quantity", 70, 150);
        ProductDatabase db = new ProductDatabaseStub();
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
    void threeFilters(){
        FilterProduct filt = FilterProduct.FilterProductFactory("expiryDate", new Date(1), new Date(3));
        FilterProduct filt2 = FilterProduct.FilterProductFactory("quantity", 70, 300);
        FilterProduct filt3 = FilterProduct.FilterProductFactory("price", 1F, 3F);
        ProductDatabase db = new ProductDatabaseStub();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        filters.add(filt);
        filters.add(filt2);
        filters.add(filt3);
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(1, ps.size());
    }
    @Test
    void filterAboveStartRangeTest(){
        FilterProduct filt = FilterProduct.FilterProductFactory("quantity", 100, null);

        ProductDatabase db = new ProductDatabaseStub();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        filters.add(filt);
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(3, ps.size());
    }

    @Test
    void filterBelowEndRangeTest(){
        FilterProduct filt = FilterProduct.FilterProductFactory("quantity", null, 100);

        ProductDatabase db = new ProductDatabaseStub();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        filters.add(filt);
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(3, ps.size());
    }

    @Test
    void filterWithNoFilters(){
        ProductDatabase db = new ProductDatabaseStub();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(5, ps.size());
    }

    @Test
    void filtersWithAndWithoutNulls(){
        FilterProduct filt = FilterProduct.FilterProductFactory("expiryDate", new Date(1), new Date(3));
        FilterProduct filt2 = FilterProduct.FilterProductFactory("quantity", 200, null);
        FilterProduct filt3 = FilterProduct.FilterProductFactory("price", null, 1.5F);
        ProductDatabase db = new ProductDatabaseStub();
        db.addProduct(new Product(3, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(4, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(5, "Apples", 400, 1.99f, new Date(3)));
        ArrayList<FilterProduct> filters = new ArrayList<>();
        filters.add(filt);
        filters.add(filt2);
        filters.add(filt3);
        ArrayList<Product> ps = (ArrayList<Product>) db.getFilteredProductList(filters);
        assertEquals(1, ps.size());
    }

}
