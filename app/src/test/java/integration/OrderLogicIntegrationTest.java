package integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.logic.OrderLogic;
import project.logic.StockManagingLogic;
import project.objects.Modifier;
import project.objects.Orderable;
import project.objects.Product;
import project.persistence.*;

import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderLogicIntegrationTest {
    private ProductDatabase pdb;
    private OrderableDatabase odb;
    private final String username = "root";
    private final String password = "root1234";
    OrderLogic logic;

    @BeforeEach
    public void initialize(){
        try{
            pdb = new ProductPersistence(username, password);
            odb = new OrderablePersistence(username, password);
            logic = new OrderLogic(pdb, odb);

            odb.empty();
            pdb.empty();

            Date date1 = new Date();
            Instant now = Instant.now();
            Instant yesterday = now.minus(1, ChronoUnit.DAYS);
            Date date2 = Date.from(yesterday);
            Date tomorrow = new Date(date1.getTime() + (1000 * 60 * 60 * 24));

            pdb.addProduct(new Product(15, "oreo", 75, 3.99F, date1));
            pdb.addProduct(new Product(14, "cheetos", 44, 2.99F, date1));

            odb.addOrderable(new Orderable("oreo", 3.99F, 12));
            odb.addOrderable(new Orderable("cheetos", 2.99F, 100));
        }
        catch(SQLException s){
            s.printStackTrace();
        }
    }

    @AfterEach
    void empty(){
        pdb.empty();
        odb.empty();
    }

    @Test
    void getOrderableList() {
        // The stub database initializes with 2 orderables.
        assertEquals(2, logic.getOrderableList().length);
    }
    @Test
    void getEmptyOrderableList() {
        odb.empty();
        // The stub database initializes with 2 orderables.
        assertEquals(0, logic.getOrderableList().length);
    }

    @Test
    void placeOrder() {

        // There should now be 3 things in stock instead of 2
        // it should be assigned barcode 2 and have quantity 100
        // finally, return no errors
        assertTrue(logic.placeOrder("oreo", "100").isEmpty());
        assertEquals(3, pdb.getProductList().size());
        assertEquals(100, pdb.getProduct(1).getQuantity());

        // Error handling

        // empty inputs
        assertFalse(logic.placeOrder("", "").isEmpty());

        // product not orderable
        assertFalse(logic.placeOrder("apple", "10").isEmpty());

        // quantity cannot be negative
        assertFalse(logic.placeOrder("oreo", "-10").isEmpty());
    }

    @Test
    void updatePrice() {
        // The price of oreo is 3.99 to begin with.
        assertEquals(3.99f, odb.getOrderable("oreo").getPrice());

        // When I update it to 2.99 that change should be reflected
        // Also, legal inputs return no errors
        assertTrue(logic.updatePrice("oreo", "2.99").isEmpty());
        assertEquals(2.99f, odb.getOrderable("oreo").getPrice());

        // Error handling

        // Empty fields
        assertFalse(logic.updatePrice("", "").isEmpty());

        // Not an orderable
        assertFalse(logic.updatePrice("apple", "0.99").isEmpty());

        // Price is negative
        assertFalse(logic.updatePrice("oreo", "-0.99").isEmpty());
    }

    @Test
    void updateShelfLife() {
        // The price of oreo is 3.99 to begin with.
        assertEquals(12, odb.getOrderable("oreo").getShelfLife());

        // When I update it to 2.99 that change should be reflected
        // Also, legal inputs return no errors
        assertTrue(logic.updateShelfLife("oreo", "120").isEmpty());
        assertEquals(120, odb.getOrderable("oreo").getShelfLife());

        // Error handling

        // Empty fields
        assertFalse(logic.updateShelfLife("", "").isEmpty());

        // Not an orderable
        assertFalse(logic.updateShelfLife("apple", "13").isEmpty());

        // Price is negative
        assertFalse(logic.updateShelfLife("oreo", "-13").isEmpty());
    }

    @Test
    void addOrderable() {
        // Add an orderable. Now there should be three to choose from
        // Also, funtion should return no errors
        assertTrue(logic.addOrderable("apple", "0.99", "40").isEmpty());
        assertEquals(3, odb.getOrderableList().size());

        // Error handling

        // Empty fields
        assertFalse(logic.addOrderable("", "", "").isEmpty());

        // Product already orderable
        assertFalse(logic.addOrderable("apple", "0.99", "40").isEmpty());

        // invalid price
        assertFalse(logic.addOrderable("apple", "-0.99", "40").isEmpty());

        // invalid shelf life
        assertFalse(logic.addOrderable("apple", "0.99", "-40").isEmpty());
    }

    @Test
    void removeOrderable() {
        // Removing an orderable should leave only one left
        // Shouldn't return any errors
        assertTrue(logic.removeOrderable("oreo").isEmpty());
        assertEquals(1, odb.getOrderableList().size());

        // Error handling

        // Empty entry
        assertFalse(logic.removeOrderable("").isEmpty());

        // Orderable can't be removed cause it's not there
        assertFalse(logic.removeOrderable("oreo").isEmpty());
    }
}
