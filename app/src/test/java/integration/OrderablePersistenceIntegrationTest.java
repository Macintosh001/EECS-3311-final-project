package integration;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import project.objects.Orderable;
import project.persistence.OrderableDatabase;
import project.persistence.OrderableDatabaseStub;
import project.persistence.OrderablePersistence;

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
public class OrderablePersistenceIntegrationTest {
    //change these values to your own username/password before running.
    private final String password = "root1234";
    private final String username = "root";
    @Test
    void getOrderableTest() throws SQLException {
        OrderableDatabase db = new OrderablePersistence(username, password);
        db.empty();
        db.addOrderable(new Orderable("cheese", 1.9F, 12));
        db.addOrderable(new Orderable("grapes", 2.9F, 10));
        db.addOrderable(new Orderable("turkey", 5.5F, 4));
        Orderable ord = db.getOrderable("cheese");
        assertEquals(12, ord.getShelfLife());
        assertEquals(1.9F, ord.getPrice());
    }

    @Test
    void getNonexistantOrderableTest() throws SQLException {
        OrderableDatabase db = new OrderablePersistence(username, password);
        db.empty();
        Orderable ord = db.getOrderable("cracker");
        assertNull(ord);
    }

    @Test
    void getOrderableListTest() throws SQLException {
        OrderableDatabase db = new OrderablePersistence(username, password);
        db.empty();
        db.addOrderable(new Orderable("cheese", 1.9F, 12));
        db.addOrderable(new Orderable("grapes", 2.9F, 10));
        db.addOrderable(new Orderable("turkey", 5.5F, 4));
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();
        ArrayList<String> names = new ArrayList<>();
        for(Orderable ord: ords){
            names.add(ord.getName());
        }
        assertTrue(names.contains("grapes"));
        assertTrue(names.contains("cheese"));
        assertTrue(names.contains("cheese"));
        assertEquals(3, ords.size());
    }

    @Test
    void getEmptyOrderableListTest() throws SQLException {
        OrderableDatabase db = new OrderablePersistence(username, password);
        db.empty();
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();
        assertEquals(0, ords.size());
    }

    @Test
    void addOrderableTest() throws SQLException {
        OrderableDatabase db = new OrderablePersistence(username, password);
        db.empty();
        db.addOrderable(new Orderable("cheese", 1.9F, 12));
        db.addOrderable(new Orderable("grapes", 2.9F, 10));
        db.addOrderable(new Orderable("turkey", 5.5F, 4));
        db.addOrderable(new Orderable("cracker", 60F, 50));
        db.addOrderable(new Orderable("carrot", 1F, 7));
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();

        ArrayList<String> names = new ArrayList<>();
        for(Orderable ord: ords){
            names.add(ord.getName());
        }

        assertEquals(5, ords.size());
        assertTrue(names.contains("cheese"));
        assertTrue(names.contains("grapes"));
        assertTrue(names.contains("turkey"));
        assertTrue(names.contains("cracker"));
        assertTrue(names.contains("carrot"));

        Orderable ord = db.getOrderable("turkey");
        assertEquals(5.5F, ord.getPrice());
        assertEquals(4, ord.getShelfLife());
    }


    @Test
    void removeOrderableTest() throws SQLException {
        OrderableDatabase db = new OrderablePersistence(username, password);
        db.empty();
        db.addOrderable(new Orderable("cheese", 1.9F, 12));
        db.addOrderable(new Orderable("grapes", 2.9F, 10));
        db.addOrderable(new Orderable("turkey", 5.5F, 4));
        db.removeOrderable("cheese");
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();
        assertEquals(2, ords.size());
        ArrayList<String> names = new ArrayList<>();
        for(Orderable ord: ords){
            names.add(ord.getName());
        }
        assertTrue(names.contains("grapes"));
        assertTrue(names.contains("turkey"));
        assertFalse(names.contains("cheese"));
    }

    @Test
    void removeNonExistentOrderableTest() throws SQLException {
        OrderableDatabase db = new OrderablePersistence(username, password);
        db.empty();
        db.addOrderable(new Orderable("grapes", 2.9F, 10));
        db.addOrderable(new Orderable("turkey", 5.5F, 4));
        db.removeOrderable("cheese");
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();
        assertEquals(2, ords.size());
        ArrayList<String> names = new ArrayList<>();
        for(Orderable ord: ords){
            names.add(ord.getName());
        }
        assertTrue(names.contains("grapes"));
        assertTrue(names.contains("turkey"));
    }

    @Test
    void removeSameOrderableTwiceTest() throws SQLException {
        OrderableDatabase db = new OrderablePersistence(username, password);
        db.empty();
        db.addOrderable(new Orderable("grapes", 2.9F, 10));
        db.addOrderable(new Orderable("turkey", 5.5F, 4));
        db.removeOrderable("grapes");
        db.removeOrderable("grapes");
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();
        assertEquals(1, ords.size());
        ArrayList<String> names = new ArrayList<>();
        for(Orderable ord: ords){
            names.add(ord.getName());
        }
        assertTrue(names.contains("turkey"));
        assertFalse(names.contains("grapes"));
    }


    @Test
    void addRemoveSequenceTest() throws SQLException {
        OrderableDatabase db = new OrderablePersistence(username, password);
        db.empty();
        db.addOrderable(new Orderable("cheese", 1.9F, 12));
        db.addOrderable(new Orderable("grapes", 2.9F, 10));
        db.removeOrderable("grapes");
        db.addOrderable(new Orderable("turkey", 5.5F, 4));
        db.addOrderable(new Orderable("cracker", 60F, 50));
        db.removeOrderable("cracker");
        db.addOrderable(new Orderable("carrot", 1F, 7));
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();

        ArrayList<String> names = new ArrayList<>();
        for(Orderable ord: ords){
            names.add(ord.getName());
        }
        assertEquals(3, ords.size());
        assertTrue(names.contains("cheese"));
        assertTrue(names.contains("turkey"));
        assertTrue(names.contains("carrot"));

        Orderable ord = db.getOrderable("turkey");
        assertEquals(5.5F, ord.getPrice());
        assertEquals(4, ord.getShelfLife());
    }

    @Test
    void replaceOrderableNewPriceTest() throws SQLException {
        Orderable cheese = new Orderable("cheese", 2.0F, 12);
        OrderableDatabase db = new OrderablePersistence(username, password);
        db.empty();
        db.addOrderable(new Orderable("cheese", 1.9F, 12));
        db.addOrderable(new Orderable("grapes", 2.9F, 10));
        db.replaceOrderable(cheese);
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();
        assertEquals(2, ords.size());
        ArrayList<String> names = new ArrayList<>();
        for(Orderable ord: ords){
            names.add(ord.getName());
        }
        Orderable ord = db.getOrderable("cheese");
        assertEquals(2.0F, ord.getPrice());
        assertEquals(12, ord.getShelfLife());
    }

    @Test
    void replaceOrderableNewShelfLifeTest() throws SQLException {
        Orderable oreo = new Orderable("cheese", 1.9F, 15);
        OrderableDatabase db = new OrderablePersistence(username, password);
        db.empty();
        db.addOrderable(new Orderable("cheese", 1.9F, 12));
        db.addOrderable(new Orderable("grapes", 2.9F, 10));
        db.replaceOrderable(oreo);
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();
        assertEquals(2, ords.size());
        ArrayList<String> names = new ArrayList<>();
        for(Orderable ord: ords){
            names.add(ord.getName());
        }

        Orderable ord = db.getOrderable("cheese");
        assertEquals(1.9F, ord.getPrice());
        assertEquals(15, ord.getShelfLife());
    }

    @Test
    void replaceNonexistentOrderableTest() throws SQLException {
        Orderable cereal = new Orderable("cereal", 3.99F, 15);
        OrderableDatabase db = new OrderablePersistence(username, password);
        db.empty();
        db.addOrderable(new Orderable("cheese", 1.9F, 12));
        db.addOrderable(new Orderable("grapes", 2.9F, 10));
        db.replaceOrderable(cereal);
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();
        assertEquals(3, ords.size());
        ArrayList<String> names = new ArrayList<>();
        for(Orderable ord: ords){
            names.add(ord.getName());
        }
        assertTrue(names.contains("cheese"));
        assertTrue(names.contains("grapes"));
        assertTrue(names.contains("cereal"));
        Orderable ord = db.getOrderable("cereal");
        assertEquals(3.99F, ord.getPrice());
        assertEquals(15, ord.getShelfLife());
    }

}
