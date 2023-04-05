package integration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.logic.StockCheckingLogic;
import project.logic.StockManagingLogic;
import project.objects.Modifier;
import project.objects.Product;
import project.persistence.*;

import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StockManagerLogicIntegrationTest {
    private ProductDatabase db;
    private ModifierDatabase mdb;
    private final String username = "root";
    private final String password = "root1234";
    StockManagingLogic logic;

    @BeforeEach
    public void initialize(){
        try{
            db = new ProductPersistence(username, password);
            mdb = new ModifierPersistence(username, password);
            logic = new StockManagingLogic(db, mdb);

            db.empty();
            mdb.empty();

            Date date1 = new Date();
            Instant now = Instant.now();
            Instant yesterday = now.minus(1, ChronoUnit.DAYS);
            Date date2 = Date.from(yesterday);
            Date tomorrow = new Date(date1.getTime() + (1000 * 60 * 60 * 24));

            db.addProduct(new Product(0, "oreo", 75, 3.99F, date2));
            db.addProduct(new Product(1, "cheeto", 44, 2.99F, date1));

            mdb.addModifier(new Modifier("oreo", 0.12F, date2, tomorrow));
            mdb.addModifier(new Modifier("cheeto", 0.1F, date2, tomorrow));
        }
        catch(SQLException s){
            s.printStackTrace();
        }
    }

    @AfterEach
    void empty(){
        db.empty();
    }

    @Test
    void removeProduct() {
        // The stub database initializes with 2 elements.
        assertEquals(2, logic.getProductList().length);

        // Test that the item was actually removed
        logic.removeProduct("0");
        assertEquals(1, logic.getProductList().length);

        // Test that an error is reported when the item is not in the database
        assertFalse(logic.removeProduct("0").isEmpty());
        assertEquals(1, logic.getProductList().length);

        // Test that an error is reported when the input is invalid
        assertFalse(logic.removeProduct("x").isEmpty());
    }

    @Test
    void removeNonExistent(){
        logic.removeProduct("100");
        assertEquals(2, logic.getProductList().length);
    }

    @Test
    void removeExpired(){
        logic.removeExpiredProducts();
        assertEquals(0, logic.getProductList().length);
    }
}
