package unit;
import org.junit.jupiter.api.Test;
import project.objects.Coupon;
import project.persistence.CouponDatabase;
import project.persistence.CouponDatabaseStub;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CouponDatabaseStubTest {
    @Test
    void getCouponTest(){
        CouponDatabase db = new CouponDatabaseStub();
        Coupon coup = db.getCoupon("coup1");
        assertEquals(0.15F, coup.getPercentOff());
    }

    @Test
    void getNonexistentCouponTest(){
        CouponDatabase db = new CouponDatabaseStub();
        Coupon coup = db.getCoupon("coup3");
        assertNull(coup);
    }

    @Test
    void getCouponList(){
        CouponDatabase db = new CouponDatabaseStub();
        ArrayList<Coupon> coups = (ArrayList<Coupon>) db.getCouponList();
        ArrayList<String> codes = new ArrayList<>();
        for(Coupon c: coups){
            codes.add(c.getCode());
        }
        assertTrue(codes.contains("coup1"));
        assertTrue(codes.contains("coup2"));
        assertEquals(2, coups.size());
    }

    @Test
    void getEmptyCouponList(){
        CouponDatabase db = new CouponDatabaseStub();
        db.empty();
        ArrayList<Coupon> coups = (ArrayList<Coupon>) db.getCouponList();
        assertEquals(0, coups.size());
    }

    @Test
    void addCouponTest(){
        CouponDatabase db = new CouponDatabaseStub();
        db.addCoupon(new Coupon("code3", 0.1F));
        db.addCoupon(new Coupon("code4", 0.3F));
        db.addCoupon(new Coupon("code5", 0.5F));
        db.addCoupon(new Coupon("code6", 0.7F));
        db.addCoupon(new Coupon("code7", 0.9F));

        ArrayList<Coupon> coups = (ArrayList<Coupon>) db.getCouponList();
        ArrayList<String> codes = new ArrayList<>();
        assertEquals(7, coups.size());
        for(Coupon c: coups){
            codes.add(c.getCode());
        }
        assertTrue(codes.contains("coup1"));
        assertTrue(codes.contains("coup2"));
        assertTrue(codes.contains("code3"));
        assertTrue(codes.contains("code4"));
        assertTrue(codes.contains("code5"));
        assertTrue(codes.contains("code6"));
        assertTrue(codes.contains("code7"));

    }

    @Test
    void addMoreCoupons(){
        CouponDatabase db = new CouponDatabaseStub();
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
        assertEquals(11, coups.size());
        for(Coupon c: coups){
            codes.add(c.getCode());
        }
        assertTrue(codes.contains("coup1"));
        assertTrue(codes.contains("coup2"));
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
    void removeCouponTest(){
        CouponDatabase db = new CouponDatabaseStub();
        db.removeCoupon("coup1");
        ArrayList<Coupon> coups = (ArrayList<Coupon>) db.getCouponList();
        ArrayList<String> codes = new ArrayList<>();
        assertEquals(1, coups.size());
        for(Coupon c: coups){
            codes.add(c.getCode());
        }
        assertFalse(codes.contains("coup1"));
        assertTrue(codes.contains("coup2"));
    }

    @Test
    void removeNonexistentCouponTest(){
        CouponDatabase db = new CouponDatabaseStub();
        db.removeCoupon("coup9");
        ArrayList<Coupon> coups = (ArrayList<Coupon>) db.getCouponList();
        ArrayList<String> codes = new ArrayList<>();
        assertEquals(2, coups.size());
        for(Coupon c: coups){
            codes.add(c.getCode());
        }
        assertTrue(codes.contains("coup1"));
        assertTrue(codes.contains("coup2"));
    }

    @Test
    void replaceCouponNewPercentTest(){
        CouponDatabase db = new CouponDatabaseStub();
        Coupon coup1 = new Coupon("coup1", 0.9F);
        db.replaceCoupon(coup1);
        ArrayList<Coupon> coups = (ArrayList<Coupon>) db.getCouponList();
        ArrayList<String> codes = new ArrayList<>();
        assertEquals(2, coups.size());
        for(Coupon c: coups){
            codes.add(c.getCode());
        }
        assertTrue(codes.contains("coup1"));
        assertTrue(codes.contains("coup2"));
        Coupon c = db.getCoupon("coup1");
        assertEquals(0.9F,c.getPercentOff());
    }

    @Test
    void replaceNonExistentCouponTest(){
        CouponDatabase db = new CouponDatabaseStub();
        Coupon coup = new Coupon("coup", 0.9F);
        db.replaceCoupon(coup);
        ArrayList<Coupon> coups = (ArrayList<Coupon>) db.getCouponList();
        ArrayList<String> codes = new ArrayList<>();
        assertEquals(3, coups.size());
        for(Coupon c: coups){
            codes.add(c.getCode());
        }
        assertTrue(codes.contains("coup1"));
        assertTrue(codes.contains("coup2"));
        assertTrue(codes.contains("coup"));
        Coupon c = db.getCoupon("coup");
        assertEquals(0.9F,c.getPercentOff());
    }

}
