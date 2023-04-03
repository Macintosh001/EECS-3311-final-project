package integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.logic.AutomationLogic;
import project.logic.OrderLogic;
import project.objects.Orderable;
import project.objects.Product;
import project.objects.RestockTask;
import project.persistence.*;

import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AutomationLogicIntegrationTest {
    private ProductDatabase pdb;
    private OrderableDatabase odb;
    private RestockTaskDatabase rdb;
    private final String username = "root";
    private final String password = "root1234";
    private AutomationLogic logic;
    private OrderLogic ol;


    @BeforeEach
    public void initialize(){
        try{
            pdb = new ProductPersistence(username, password);
            odb = new OrderablePersistence(username, password);
            rdb = new RestockTaskPersistence(username, password);
            ol = new OrderLogic(pdb, odb);
            logic = new AutomationLogic(rdb, pdb, odb, ol);

            odb.empty();
            pdb.empty();
            rdb.empty();

            Date date1 = new Date();
            Instant now = Instant.now();
            Instant yesterday = now.minus(1, ChronoUnit.DAYS);
            Date date2 = Date.from(yesterday);
            Date tomorrow = new Date(date1.getTime() + (1000 * 60 * 60 * 24));

            pdb.addProduct(new Product(15, "oreo", 75, 3.99F, date1));
            pdb.addProduct(new Product(14, "cheeto", 2, 2.99F, date1));

            odb.addOrderable(new Orderable("oreo", 3.99F, 12));
            odb.addOrderable(new Orderable("cheeto", 2.99F, 100));


            rdb.addRestockTask(new RestockTask("cheeto", 3, 15));
        }
        catch(SQLException s){
            s.printStackTrace();
        }
    }

    @AfterEach
    void empty(){
        pdb.empty();
        odb.empty();
        rdb.empty();
    }

    @Test
    void getRestockTaskList(){
        assertEquals(1, logic.getRestockTaskTable().length);
    }

    @Test
    void getEmptyRestockTaskList(){
        rdb.empty();
        assertEquals(0, logic.getRestockTaskTable().length);
    }

    @Test
    void addTask(){
        assertTrue(this.logic.addRestockTask("oreo", "1", "2").isEmpty());
        assertEquals(2, logic.getRestockTaskTable().length);
        rdb.empty();
        //errors
        assertFalse(this.logic.addRestockTask("oreo", "", "2").isEmpty());
        assertFalse(this.logic.addRestockTask("candy", "1", "2").isEmpty());
        assertFalse(this.logic.addRestockTask("oreo", "1", "").isEmpty());
        assertFalse(this.logic.addRestockTask("", "1", "2").isEmpty());
    }

    @Test
    void updateTask(){
        assertTrue(this.logic.updateRestockTask("cheeto", "1", "2").isEmpty());
        //errors
        assertFalse(this.logic.updateRestockTask("oreo", "1", "2").isEmpty());
        assertFalse(this.logic.updateRestockTask("", "1", "2").isEmpty());
        assertFalse(this.logic.updateRestockTask("oreo", "1", "").isEmpty());
    }

    @Test
    void removeTask(){
        assertTrue(this.logic.removeRestockTask("cheeto").isEmpty());
        assertEquals(0, logic.getRestockTaskTable().length);
        assertFalse(this.logic.removeRestockTask("cheeto").isEmpty());
        assertFalse(this.logic.removeRestockTask("oreo").isEmpty());
        assertFalse(this.logic.removeRestockTask("").isEmpty());
    }

    @Test
    void automate(){
        logic.automate();
        assertEquals(2, pdb.getProduct(14).getQuantity());
    }

}
