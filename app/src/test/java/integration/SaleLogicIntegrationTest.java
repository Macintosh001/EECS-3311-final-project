package integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.logic.SaleLogic;
import project.logic.StockCheckingLogic;
import project.objects.Coupon;
import project.objects.ErrorMsg;
import project.objects.Modifier;
import project.objects.Product;
import project.persistence.*;

import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SaleLogicIntegrationTest {
   private ProductDatabase db;
   private ModifierDatabase mdb;
   private CouponDatabase cdb;
   private SaleLogic saleLogic;
    private final String username = "root";
    private final String password = "root1234";

    @BeforeEach
    public void initialize(){
        try{
            db = new ProductPersistence(username, password);
            mdb = new ModifierPersistence(username, password);
            cdb = new CouponPersistence(username, password);
            saleLogic = new SaleLogic(db, cdb, mdb);
            db.empty();
            mdb.empty();
            cdb.empty();
            Date date1 = new Date();
            Instant now = Instant.now();

            Instant yesterday = now.minus(1, ChronoUnit.DAYS);
            Date date2 = Date.from(yesterday);
            Date tomorrow = new Date(date1.getTime() + (1000 * 60 * 60 * 24));
            db.addProduct(new Product(0, "oreo", 75, 3.99F, date1));
            db.addProduct(new Product(1, "cheeto", 44, 2.99F, date1));
            mdb.addModifier(new Modifier("oreo", 0.12F, date2, tomorrow));
            mdb.addModifier(new Modifier("cheeto", 0.1F, date2, tomorrow));
            cdb.addCoupon(new Coupon("coup1", 0.15F));
            cdb.addCoupon(new Coupon("coup2", 0.25F));
        }
        catch(SQLException s){
            s.printStackTrace();
        }
    }

    @AfterEach
    void empty(){
        db.empty();
        mdb.empty();
    }

    @Test
    public void scanInvalidBarcode() {
        List<ErrorMsg> errors = saleLogic.scan("invalid barcode");
        assertEquals(1, errors.size());
        assertEquals("Invalid barcode format", errors.get(0).getMessage());
    }

    @Test
    public void scanProductNotFound() {
        List<ErrorMsg> errors = saleLogic.scan("111");
        assertEquals(1, errors.size());
        assertEquals("Product not found in inventory", errors.get(0).getMessage());
    }

    @Test
    public void scanProductSuccess() {
        List<ErrorMsg> errors = saleLogic.scan("0");
        assertTrue(errors.isEmpty());
        String[][] cartTable = saleLogic.getCartTable();
        assertEquals(1, cartTable.length);
        assertEquals("0", cartTable[0][0]);
        assertEquals("oreo", cartTable[0][1]);
        assertEquals("1", cartTable[0][2]);
        assertEquals("4.47", cartTable[0][3]);
    }

    @Test
    public void clearShoppingCart() {
        saleLogic.scan("0");
        saleLogic.scan("1");
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
        saleLogic.applyCoupon("coup1");
        List<ErrorMsg> errors = saleLogic.applyCoupon("coup1");
        assertEquals(1, errors.size());
        assertEquals("Coupon already applied", errors.get(0).getMessage());
    }

    @Test
    public void applyValidCouponCode() {
        saleLogic.scan("0");
        List<ErrorMsg> errors = saleLogic.applyCoupon("coup1");
        assertTrue(errors.isEmpty());
        String total = saleLogic.getTotal();
        assertEquals("3.80", total);
    }

    @Test
    public void buySuccess() {
        saleLogic.scan("0");
        List<ErrorMsg> errors = saleLogic.buy();
        assertTrue(errors.isEmpty());
        String[][] cartTable = saleLogic.getCartTable();
        assertEquals(0, cartTable.length);
        Product product = db.getProduct(0);
        assertEquals(74, product.getQuantity());
    }

    @Test
    public void buyProductOutOfStock() {
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
