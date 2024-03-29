package unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import project.logic.SaleLogic;
import project.objects.*;
import project.persistence.*;

import java.util.List;

public class SaleLogicTest {



    @Test
    public void scanInvalidBarcode() {
        ProductDatabase productDB = new ProductDatabaseStub();
        CouponDatabase couponDB = new CouponDatabaseStub();
        ModifierDatabase modifierDB = new ModifierDatabaseStub();
        SaleLogic saleLogic = new SaleLogic(productDB, couponDB, modifierDB);
        
        List<ErrorMsg> errors = saleLogic.scan("invalid barcode");
        assertEquals(1, errors.size());
        assertEquals("Invalid barcode format", errors.get(0).getMessage());
    }
    
    @Test
    public void scanProductNotFound() {
        ProductDatabase productDB = new ProductDatabaseStub();
        CouponDatabase couponDB = new CouponDatabaseStub();
        ModifierDatabase modifierDB = new ModifierDatabaseStub();
        SaleLogic saleLogic = new SaleLogic(productDB, couponDB, modifierDB);

        List<ErrorMsg> errors = saleLogic.scan("111");
        assertEquals(1, errors.size());
        assertEquals("Product not found in inventory", errors.get(0).getMessage());
    }
    
    @Test
    public void scanProductSuccess() {
        ProductDatabase productDB = new ProductDatabaseStub();
        CouponDatabase couponDB = new CouponDatabaseStub();
        ModifierDatabase modifierDB = new ModifierDatabaseStub();
        SaleLogic saleLogic = new SaleLogic(productDB, couponDB, modifierDB);

        List<ErrorMsg> errors = saleLogic.scan("0");
        assertTrue(errors.isEmpty());
        String[][] cartTable = saleLogic.getCartTable();
        assertEquals(1, cartTable.length);
        assertEquals("0", cartTable[0][0]);
        assertEquals("oreo", cartTable[0][1]);
        assertEquals("1", cartTable[0][2]);
        assertEquals("7.58", cartTable[0][3]);
    }
    
    @Test
    public void scanSameProductMultipleTimes() {
        ProductDatabase productDB = new ProductDatabaseStub();
        CouponDatabase couponDB = new CouponDatabaseStub();
        ModifierDatabase modifierDB = new ModifierDatabaseStub();
        SaleLogic saleLogic = new SaleLogic(productDB, couponDB, modifierDB);

        saleLogic.scan("0");
        saleLogic.scan("0");
        String[][] cartTable = saleLogic.getCartTable();
        assertEquals(1, cartTable.length);
        assertEquals("0", cartTable[0][0]);
        assertEquals("oreo", cartTable[0][1]);
        assertEquals("2", cartTable[0][2]);
        assertEquals("7.58", cartTable[0][3]);
    }
    
    @Test
    public void clearShoppingCart() {
        ProductDatabase productDB = new ProductDatabaseStub();
        CouponDatabase couponDB = new CouponDatabaseStub();
        ModifierDatabase modifierDB = new ModifierDatabaseStub();
        SaleLogic saleLogic = new SaleLogic(productDB, couponDB, modifierDB);

        saleLogic.scan("0");
        saleLogic.scan("1");
        saleLogic.clearShoppingCart();
        String[][] cartTable = saleLogic.getCartTable();
        assertEquals(0, cartTable.length);
    }
    
    @Test
    public void applyInvalidCouponCode() {
        ProductDatabase productDB = new ProductDatabaseStub();
        CouponDatabase couponDB = new CouponDatabaseStub();
        ModifierDatabase modifierDB = new ModifierDatabaseStub();
        SaleLogic saleLogic = new SaleLogic(productDB, couponDB, modifierDB);

        List<ErrorMsg> errors = saleLogic.applyCoupon("INVALIDCODE");
        assertEquals(1, errors.size());
        assertEquals("Invalid coupon code", errors.get(0).getMessage());
    }
    
    @Test
    public void applySameCouponCodeTwice() {
        ProductDatabase productDB = new ProductDatabaseStub();
        CouponDatabase couponDB = new CouponDatabaseStub();
        ModifierDatabase modifierDB = new ModifierDatabaseStub();
        SaleLogic saleLogic = new SaleLogic(productDB, couponDB, modifierDB);

    	saleLogic.applyCoupon("coup1");
    	List<ErrorMsg> errors = saleLogic.applyCoupon("coup1");
    	assertEquals(1, errors.size());
    	assertEquals("Coupon already applied", errors.get(0).getMessage());
    }
    
    @Test
    public void applyValidCouponCode() {
        ProductDatabase productDB = new ProductDatabaseStub();
        CouponDatabase couponDB = new CouponDatabaseStub();
        ModifierDatabase modifierDB = new ModifierDatabaseStub();
        SaleLogic saleLogic = new SaleLogic(productDB, couponDB, modifierDB);

        saleLogic.scan("0");
        List<ErrorMsg> errors = saleLogic.applyCoupon("coup1");
        assertTrue(errors.isEmpty());
        String total = saleLogic.getTotal();
        assertEquals("6.44", total);
    }

    @Test
    public void buySuccess() {
        ProductDatabase productDB = new ProductDatabaseStub();
        CouponDatabase couponDB = new CouponDatabaseStub();
        ModifierDatabase modifierDB = new ModifierDatabaseStub();
        SaleLogic saleLogic = new SaleLogic(productDB, couponDB, modifierDB);

        saleLogic.scan("0");
        List<ErrorMsg> errors = saleLogic.buy();
        assertTrue(errors.isEmpty());
        String[][] cartTable = saleLogic.getCartTable();
        assertEquals(0, cartTable.length);
        Product product = productDB.getProduct(0);
        assertEquals(74, product.getQuantity());
    }

    @Test
    public void buyProductOutOfStock() {
        ProductDatabase productDB = new ProductDatabaseStub();
        CouponDatabase couponDB = new CouponDatabaseStub();
        ModifierDatabase modifierDB = new ModifierDatabaseStub();
        SaleLogic saleLogic = new SaleLogic(productDB, couponDB, modifierDB);

        saleLogic.scan("0");
        for (int i = 0; i < 75; i++) {
            saleLogic.scan("1");
        }
        List<ErrorMsg> errors = saleLogic.buy();
        assertEquals(1, errors.size());
        assertEquals("Product out of stock", errors.get(0).getMessage());
        String[][] cartTable = saleLogic.getCartTable();
        assertEquals(2, cartTable.length);
    }
}
