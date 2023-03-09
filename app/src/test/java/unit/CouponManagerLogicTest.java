package unit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.logic.CouponManagerLogic;
import project.persistence.CouponDatabase;
import project.persistence.CouponDatabaseStub;

import static org.junit.jupiter.api.Assertions.*;

class CouponManagerLogicTest {

    CouponDatabase cdb;
    CouponManagerLogic logic;
    @BeforeEach
    void setUp() {
        cdb = new CouponDatabaseStub();
        logic = new CouponManagerLogic(cdb);
    }

    @AfterEach
    void tearDown() {
        cdb = null;
        logic = null;
    }

    @Test
    void getCouponTable() {
        assertEquals(2, logic.getCouponTable().length);
    }

    @Test
    void addCoupon() {
        assertTrue(logic.addCoupon("coup3", "35").isEmpty());
        assertEquals(3, logic.getCouponTable().length);
    }

    @Test
    void removeCoupon() {
        logic.addCoupon("coup3", "35");
        assertEquals(3, logic.getCouponTable().length);
        logic.removeCoupon("coup3");
        assertEquals(2, logic.getCouponTable().length);
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