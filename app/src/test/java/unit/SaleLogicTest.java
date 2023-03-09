package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SaleLogicTest {
    private SaleLogic saleLogic;
    private Map<Integer, Product> productDB;
    private Map<String, Coupon> couponDB;
    
    @Before
    public void setUp() {
        productDB = new HashMap<>();
        productDB.put(123456, new Product(123456, "test product", 10, 1.0, null));
        productDB.put(234567, new Product(234567, "test product 2", 5, 2.0, null));
        
        couponDB = new HashMap<>();
        couponDB.put("TESTCODE", new Coupon("TESTCODE", 0.1));
        
        saleLogic = new SaleLogic(new ProductDatabase(productDB), new CouponDatabase(couponDB));
    }
    
    @Test
    public void scanInvalidBarcode() {
        List<ErrorMsg> errors = saleLogic.scan("invalid barcode");
        assertEquals(1, errors.size());
        assertEquals("Invalid barcode format", errors.get(0).getMessage());
    }
    
    @Test
    public void scanProductNotFound() {
        List<ErrorMsg> errors = saleLogic.scan("111111");
        assertEquals(1, errors.size());
        assertEquals("Product not found in inventory", errors.get(0).getMessage());
    }
    
    @Test
    public void scanProductOutOfStock() {
        for (int i = 0; i < 10; i++) {
            saleLogic.scan("123456");
        }
        List<ErrorMsg> errors = saleLogic.scan("123456");
        assertEquals(1, errors.size());
        assertEquals("Product out of stock", errors.get(0).getMessage());
    }
    
    @Test
    public void scanProductSuccess() {
        List<ErrorMsg> errors = saleLogic.scan("123456");
        assertTrue(errors.isEmpty());
        String[][] cartTable = saleLogic.getCartTable();
        assertEquals(1, cartTable.length);
        assertEquals("123456", cartTable[0][0]);
        assertEquals("test product", cartTable[0][1]);
        assertEquals("1", cartTable[0][2]);
        assertEquals("1.00", cartTable[0][3]);
    }
    
    @Test
    public void scanSameProductMultipleTimes() {
        saleLogic.scan("123456");
        saleLogic.scan("123456");
        String[][] cartTable = saleLogic.getCartTable();
        assertEquals(1, cartTable.length);
        assertEquals("123456", cartTable[0][0]);
        assertEquals("test product", cartTable[0][1]);
        assertEquals("2", cartTable[0][2]);
        assertEquals("1.00", cartTable[0][3]);
    }
    
    @Test
    public void clearShoppingCart() {
        saleLogic.scan("123456");
        saleLogic.scan("234567");
        saleLogic.clearShoppingCart();
        String[][] cartTable = saleLogic.getCartTable();
        assertEquals(0, cartTable.length);
    }
    
    @Test
    public void applyInvalidCouponCode() {
        List<ErrorMsg> errors = saleLogic.applyCoupon("INVALIDCODE");
        assertEquals(1, errors.size());
        assertEquals("Invalid coupon code", errors.get(0).getMessage());
    }
    
    @Test
    public void applySameCouponCodeTwice() {
    saleLogic.applyCoupon("TESTCODE");
    List<ErrorMsg> errors = saleLogic.applyCoupon("TESTCODE");
    assertEquals(1, errors.size());
    assertEquals("Coupon already applied", errors.get(0).getMessage());
    }
    
    @Test
    public void applyValidCouponCode() {
        List<ErrorMsg> errors = saleLogic.applyCoupon("TESTCODE");
        assertTrue(errors.isEmpty());
        String total = saleLogic.getTotal();
        assertEquals("1.35", total);
    }

    @Test
    public void buySuccess() {
        saleLogic.scan("123456");
        List<ErrorMsg> errors = saleLogic.buy();
        assertTrue(errors.isEmpty());
        String[][] cartTable = saleLogic.getCartTable();
        assertEquals(0, cartTable.length);
        Product product = productDB.get(123456);
        assertEquals(9, product.getQuantity());
    }

    @Test
    public void buyProductOutOfStock() {
        saleLogic.scan("123456");
        for (int i = 0; i < 10; i++) {
            saleLogic.scan("234567");
        }
        List<ErrorMsg> errors = saleLogic.buy();
        assertEquals(1, errors.size());
        assertEquals("Product out of stock", errors.get(0).getMessage());
        String[][] cartTable = saleLogic.getCartTable();
        assertEquals(2, cartTable.length);
    }
}