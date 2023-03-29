package unit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.logic.AutomationLogic;
import project.logic.OrderLogic;
import project.persistence.*;

import static org.junit.jupiter.api.Assertions.*;

class AutomationLogicTest {

    RestockTaskDatabase rtb;
    ProductDatabase pdb;
    OrderableDatabaseStub odb;
    OrderLogic orderLogic;
    AutomationLogic aLogic;
    @BeforeEach
    void setUp() {
        this.rtb = new RestockTaskDatabaseStub();
        this.pdb = new ProductDatabaseStub();
        this.odb = new OrderableDatabaseStub();
        this.orderLogic = new OrderLogic(pdb,odb);
        this.aLogic = new AutomationLogic(rtb,pdb,odb,orderLogic);
    }

    @AfterEach
    void tearDown() {
        rtb = null;
        pdb = null;
        odb = null;
        orderLogic = null;
        aLogic = null;
    }

    @Test
    void getRestockTaskTable() {
        assertEquals(2, aLogic.getRestockTaskTable().length);
    }

    @Test
    void addRestockTask() {
        orderLogic.addOrderable("dunkaroos", "3.99", "50");
        orderLogic.placeOrder("dunkaroos", "100");
        assertTrue(aLogic.addRestockTask("dunkaroos", "20", "50").isEmpty());
        assertEquals(3, aLogic.getRestockTaskTable().length);
    }

    @Test
    void removeRestockTask() {
        aLogic.removeRestockTask("oreos");
        assertEquals(1, aLogic.getRestockTaskTable().length);
    }

    @Test
    void automate() {
    }
}