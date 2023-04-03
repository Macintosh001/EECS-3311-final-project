package integration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.logic.CouponManagerLogic;
import project.logic.StockManagingLogic;
import project.objects.Coupon;
import project.objects.Modifier;
import project.objects.Product;
import project.persistence.CouponDatabase;
import project.persistence.CouponPersistence;
import project.persistence.ModifierPersistence;
import project.persistence.ProductPersistence;


import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CouponManagerIntegrationTest {

    private CouponDatabase cdb;
    private final String username = "root";
    private final String password = "root1234";
    CouponManagerLogic logic;

    @BeforeEach
    public void initialize(){
        try{
            cdb = new CouponPersistence(username, password);
            logic = new CouponManagerLogic(cdb);

            cdb.empty();
            cdb.addCoupon(new Coupon("coup1", 0.15F));
            cdb.addCoupon(new Coupon("coup2", 0.25F));
        }
        catch(SQLException s){
            s.printStackTrace();
        }
    }

    @AfterEach
    void empty(){
        cdb.empty();
    }

    @Test
    void getCouponTable() {
        assertEquals(2, logic.getCouponTable().length);
    }

    @Test
    void getEmptyCouponTable() {
        cdb.empty();
        assertEquals(0, logic.getCouponTable().length);
    }

    @Test
    void addCoupon() {
        assertTrue(logic.addCoupon("coup3", "35").isEmpty());
        assertEquals(3, logic.getCouponTable().length);
    }
    @Test
    void addRepeatCoupon() {
        assertEquals(1, logic.addCoupon("coup2", "35").size());
        assertEquals(2, logic.getCouponTable().length);
    }
    @Test
    void addEmptyCoupon() {
        assertEquals(1, logic.addCoupon("", "35").size());
        assertEquals(2, logic.getCouponTable().length);
    }

    @Test
    void removeCoupon() {
        logic.addCoupon("coup3", "35");
        assertEquals(3, logic.getCouponTable().length);
        logic.removeCoupon("coup3");
        assertEquals(2, logic.getCouponTable().length);
    }

    @Test
    void removeNoCoupon() {
        logic.addCoupon("coup3", "35");
        assertEquals(3, logic.getCouponTable().length);
        logic.removeCoupon("Incorrect");
        assertEquals(3, logic.getCouponTable().length);
    }


    @Test
    void updateCoupon() {
        logic.addCoupon("coup3", "35");
        assertEquals("coup3", cdb.getCoupon("coup3").getCode());
        assertEquals("0.35", cdb.getCoupon("coup3").getPercentOff().toString());
        logic.updateCoupon("coup3","50");
        assertEquals("coup3", cdb.getCoupon("coup3").getCode());
        assertEquals("0.5", cdb.getCoupon("coup3").getPercentOff().toString());
    }

}
