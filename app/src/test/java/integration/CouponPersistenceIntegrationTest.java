package integration;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import project.objects.Coupon;
import project.persistence.CouponDatabase;
import project.persistence.CouponDatabaseStub;
import project.persistence.CouponPersistence;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Test class for the real database; this class forces predictable behavior by clearing the database
 * before performing operations. This is a type of integration test.
 * Note1 : This class modifies the database
 * Note2 : This class, unlike the application, must have the password "root1234" and the username "root"
 * setup on the database; for this with access to this class, for modification, simply change the
 * values of the fields of this class to be your own username and password if you are unwilling to change these values.
 */
public class CouponPersistenceIntegrationTest {
    //change these values to your own username/password before running.
    private final String password = "root1234";
    private final String username = "root";

    @Test
    void getCouponTest() throws SQLException {
        CouponDatabase db = new CouponPersistence(username, password);
        db.empty();
        db.addCoupon(new Coupon("code3", 0.1F));
        db.addCoupon(new Coupon("code4", 0.3F));
        db.addCoupon(new Coupon("code5", 0.5F));
        Coupon coup = db.getCoupon("code3");
        assertEquals(0.1F, coup.getPercentOff());
    }

    @Test
    void getNonexistentCouponTest() throws SQLException {
        CouponDatabase db = new CouponPersistence(username, password);
        db.empty();
        Coupon coup = db.getCoupon("coup3");
        assertNull(coup);
    }

    @Test
    void getCouponList() throws SQLException {
        CouponDatabase db = new CouponPersistence(username, password);
        db.empty();
        db.addCoupon(new Coupon("code3", 0.1F));
        db.addCoupon(new Coupon("code4", 0.3F));
        db.addCoupon(new Coupon("code5", 0.5F));
        ArrayList<Coupon> coups = (ArrayList<Coupon>) db.getCouponList();
        ArrayList<String> codes = new ArrayList<>();
        for(Coupon c: coups){
            codes.add(c.getCode());
        }
        assertTrue(codes.contains("code3"));
        assertTrue(codes.contains("code4"));
        assertTrue(codes.contains("code5"));
        assertEquals(3, coups.size());
    }


    @Test
    void getEmptyCouponList() throws SQLException {
        CouponDatabase db = new CouponPersistence(username, password);
        db.empty();
        ArrayList<Coupon> coups = (ArrayList<Coupon>) db.getCouponList();
        assertEquals(0, coups.size());
    }

    @Test
    void addCouponTest() throws SQLException {
        CouponDatabase db = new CouponPersistence(username, password);
        db.empty();
        db.addCoupon(new Coupon("code3", 0.1F));
        db.addCoupon(new Coupon("code4", 0.3F));
        db.addCoupon(new Coupon("code5", 0.5F));
        db.addCoupon(new Coupon("code6", 0.7F));
        db.addCoupon(new Coupon("code7", 0.9F));

        ArrayList<Coupon> coups = (ArrayList<Coupon>) db.getCouponList();
        ArrayList<String> codes = new ArrayList<>();
        assertEquals(5, coups.size());
        for(Coupon c: coups){
            codes.add(c.getCode());
        }

        assertTrue(codes.contains("code3"));
        assertTrue(codes.contains("code4"));
        assertTrue(codes.contains("code5"));
        assertTrue(codes.contains("code6"));
        assertTrue(codes.contains("code7"));

    }

    @Test
    void addMoreCoupons() throws SQLException {
        CouponDatabase db = new CouponPersistence(username, password);
        db.empty();
        db.addCoupon(new Coupon("code3", 0.1F));
        db.addCoupon(new Coupon("code4", 0.3F));
        db.addCoupon(new Coupon("code5", 0.5F));
        db.addCoupon(new Coupon("code6", 0.7F));
        db.addCoupon(new Coupon("code7", 0.9F));
        db.addCoupon(new Coupon("code8", 0.3F));
        db.addCoupon(new Coupon("code9", 0.5F));
        db.addCoupon(new Coupon("code10", 0.7F));
        db.addCoupon(new Coupon("code11", 0.9F));

        ArrayList<Coupon> coups = (ArrayList<Coupon>) db.getCouponList();
        ArrayList<String> codes = new ArrayList<>();
        assertEquals(9, coups.size());
        for(Coupon c: coups){
            codes.add(c.getCode());
        }

        assertTrue(codes.contains("code3"));
        assertTrue(codes.contains("code4"));
        assertTrue(codes.contains("code5"));
        assertTrue(codes.contains("code6"));
        assertTrue(codes.contains("code7"));
        assertTrue(codes.contains("code8"));
        assertTrue(codes.contains("code9"));
        assertTrue(codes.contains("code10"));
        assertTrue(codes.contains("code11"));
    }

    @Test
    void removeCouponTest() throws SQLException {
        CouponDatabase db = new CouponPersistence(username, password);
        db.empty();
        db.addCoupon(new Coupon("code3", 0.1F));
        db.addCoupon(new Coupon("code4", 0.3F));
        db.addCoupon(new Coupon("code5", 0.5F));
        db.removeCoupon("code3");
        ArrayList<Coupon> coups = (ArrayList<Coupon>) db.getCouponList();
        ArrayList<String> codes = new ArrayList<>();
        assertEquals(2, coups.size());
        for(Coupon c: coups){
            codes.add(c.getCode());
        }
        assertFalse(codes.contains("code3"));
        assertTrue(codes.contains("code4"));
        assertTrue(codes.contains("code5"));
    }

    @Test
    void removeNonexistentCouponTest() throws SQLException {
        CouponDatabase db = new CouponPersistence(username, password);
        db.empty();
        db.removeCoupon("coup9");
        ArrayList<Coupon> coups = (ArrayList<Coupon>) db.getCouponList();
        ArrayList<String> codes = new ArrayList<>();
        assertEquals(0, coups.size());
        for(Coupon c: coups){
            codes.add(c.getCode());
        }
    }

    @Test
    void replaceCouponNewPercentTest() throws SQLException {
        CouponDatabase db = new CouponPersistence(username, password);
        db.empty();
        db.addCoupon(new Coupon("code3", 0.1F));
        db.addCoupon(new Coupon("code4", 0.3F));
        db.addCoupon(new Coupon("code5", 0.5F));
        Coupon code3 = new Coupon("code3", 9F);
        db.replaceCoupon(code3);
        ArrayList<Coupon> coups = (ArrayList<Coupon>) db.getCouponList();
        ArrayList<String> codes = new ArrayList<>();
        assertEquals(3, coups.size());
        for(Coupon c: coups){
            codes.add(c.getCode());
        }
        Coupon c = db.getCoupon("code3");
        assertEquals(9F,c.getPercentOff());
    }

    @Test
    void replaceNonExistentCouponTest() throws SQLException {
        CouponDatabase db = new CouponPersistence(username, password);
        db.empty();
        Coupon coup = new Coupon("coup", 0.9F);
        db.replaceCoupon(coup);
        ArrayList<Coupon> coups = (ArrayList<Coupon>) db.getCouponList();
        ArrayList<String> codes = new ArrayList<>();
        assertEquals(1, coups.size());
        for(Coupon c: coups){
            codes.add(c.getCode());
        }
        assertTrue(codes.contains("coup"));
        Coupon c = db.getCoupon("coup");
        assertEquals(0.9F,c.getPercentOff());
    }

}
