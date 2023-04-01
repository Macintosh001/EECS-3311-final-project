package integration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.logic.StockCheckingLogic;
import project.objects.Modifier;
import project.objects.Product;
import project.persistence.*;

import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockCheckingIntegrationTest {
   private ProductDatabase db;
   private ModifierDatabase mdb;
   private final String username = "root";
   private final String password = "root1234";
    StockCheckingLogic logic;

    @BeforeEach
    public void initialize(){
        try{
            db = new ProductPersistence(username, password);
            mdb = new ModifierPersistence(username, password);
            logic = new StockCheckingLogic(db, mdb);
            db.empty();
            mdb.empty();
            Date date1 = new Date();
            Instant now = Instant.now();
            Instant yesterday = now.minus(1, ChronoUnit.DAYS);
            Date date2 = Date.from(yesterday);
            Date tomorrow = new Date(date1.getTime() + (1000 * 60 * 60 * 24));
            db.addProduct(new Product(0, "oreo", 75, 3.99F, date1));
            db.addProduct(new Product(1, "cheetos", 44, 2.99F, date1));
            mdb.addModifier(new Modifier("oreo", 0.12F, date2, tomorrow));
            mdb.addModifier(new Modifier("cheetos", 0.1F, date2, tomorrow));
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
    void getProductList() {
        // The stub database initializes with 2 elements.
        assertEquals(2, logic.getProductList().length);
    }

    @Test
    void applyModifierTest() {
        // The stub database initializes with 2 elements.
        String[][] products = logic.getProductList();
        for(int i = 0; i < products.length; i++){
            if(products[i][1].compareTo("oreo") == 0){
                Float temp = Float.parseFloat(products[i][3]);
                assertEquals(3.99F + (3.99F*0.12F), temp, 0.1);
            }
        }
    }

    @Test
    void getEmptyProductList() {
        db.empty();
        assertEquals(0, logic.getProductList().length);
    }

    @Test
    void getExpiredList(){
        assertEquals(2, logic.getExpiredList().length);
    }

    @Test
    void getFilteredList() {
        // Test the filter on quantities
        assertEquals(1, logic.getFilteredList("", "",
                "", "50",
                "", "").getResult().length);

        // Test the filter on prices
        assertEquals(1, logic.getFilteredList("2.0", "3.0",
                "", "",
                "", "").getResult().length);

        // Test invalid inputs
        // There should be one error for each incorrect entry
        assertEquals(6, logic.getFilteredList("x", "x",
                "x", "x",
                "x", "x").getError().size());
    }

    @Test
    void getNoFilteredList() {
        assertEquals(2, logic.getFilteredList("", "",
                "", "",
                "", "").getResult().length);
    }

}
