package unit;
import org.junit.jupiter.api.Test;
import project.objects.Orderable;
import project.persistence.OrderableDatabase;
import project.persistence.OrderableDatabaseStub;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class OrderableDatabaseStubTest {


    @Test
    void getOrderableTest(){
        OrderableDatabase db = new OrderableDatabaseStub();
        Orderable ord = db.getOrderable("oreo");
        assertEquals(12, ord.getShelfLife());
        assertEquals(3.99F, ord.getPrice());
    }

    @Test
    void getNonexistantOrderableTest(){
        OrderableDatabase db = new OrderableDatabaseStub();
        Orderable ord = db.getOrderable("cracker");
        assertNull(ord);
    }

    @Test
    void getOrderableListTest(){
        OrderableDatabase db = new OrderableDatabaseStub();
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();
        ArrayList<String> names = new ArrayList<>();
        for(Orderable ord: ords){
            names.add(ord.getName());
        }
        assertTrue(names.contains("oreo"));
        assertTrue(names.contains("cheeto"));
        assertEquals(2, ords.size());
    }

    @Test
    void getEmptyOrderableListTest(){
        OrderableDatabase db = new OrderableDatabaseStub();
        db.removeOrderable("oreo");
        db.removeOrderable("cheeto");
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();

        assertEquals(0, ords.size());
    }

    @Test
    void addOrderableTest(){
        OrderableDatabase db = new OrderableDatabaseStub();
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
        assertEquals(7, ords.size());
        assertTrue(names.contains("oreo"));
        assertTrue(names.contains("cheeto"));
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
    void addLotsOfOrderablesTest(){
        OrderableDatabase db = new OrderableDatabaseStub();
        db.addOrderable(new Orderable("cheese", 1.9F, 12));
        db.addOrderable(new Orderable("grapes", 2.9F, 10));
        db.addOrderable(new Orderable("turkey", 5.5F, 4));
        db.addOrderable(new Orderable("cracker", 60F, 50));
        db.addOrderable(new Orderable("carrot", 1F, 7));
        db.addOrderable(new Orderable("cheese2", 1.9F, 12));
        db.addOrderable(new Orderable("grapes2", 2.9F, 10));
        db.addOrderable(new Orderable("turkey2", 5.5F, 4));
        db.addOrderable(new Orderable("cracker2", 60F, 50));
        db.addOrderable(new Orderable("carrot2", 1F, 7));
        db.addOrderable(new Orderable("cheese3", 1.9F, 12));
        db.addOrderable(new Orderable("grapes3", 2.9F, 10));
        db.addOrderable(new Orderable("turkey3", 5.5F, 4));
        db.addOrderable(new Orderable("cracker3", 60F, 50));
        db.addOrderable(new Orderable("carrot3", 1F, 7));
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();

        ArrayList<String> names = new ArrayList<>();
        for(Orderable ord: ords){
            names.add(ord.getName());
        }
        assertEquals(17, ords.size());
        assertTrue(names.contains("oreo"));
        assertTrue(names.contains("cheeto"));
        assertTrue(names.contains("cheese"));
        assertTrue(names.contains("grapes"));
        assertTrue(names.contains("turkey"));
        assertTrue(names.contains("cracker"));
        assertTrue(names.contains("carrot"));
        assertTrue(names.contains("cheese2"));
        assertTrue(names.contains("grapes2"));
        assertTrue(names.contains("turkey2"));
        assertTrue(names.contains("cracker2"));
        assertTrue(names.contains("carrot2"));
        assertTrue(names.contains("cheese3"));
        assertTrue(names.contains("grapes3"));
        assertTrue(names.contains("turkey3"));
        assertTrue(names.contains("cracker3"));
        assertTrue(names.contains("carrot3"));

        Orderable ord = db.getOrderable("turkey");
        assertEquals(5.5F, ord.getPrice());
        assertEquals(4, ord.getShelfLife());
    }
    @Test
    void removeOrderableTest(){
        OrderableDatabase db = new OrderableDatabaseStub();
        db.removeOrderable("oreo");
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();
        assertEquals(1, ords.size());
        ArrayList<String> names = new ArrayList<>();
        for(Orderable ord: ords){
            names.add(ord.getName());
        }
        assertTrue(names.contains("cheeto"));
        assertFalse(names.contains("oreo"));
    }

    @Test
    void removeNonExistentOrderableTest(){
        OrderableDatabase db = new OrderableDatabaseStub();
        db.removeOrderable("cheese");
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();
        assertEquals(2, ords.size());
        ArrayList<String> names = new ArrayList<>();
        for(Orderable ord: ords){
            names.add(ord.getName());
        }
        assertTrue(names.contains("cheeto"));
        assertTrue(names.contains("oreo"));
    }

    @Test
    void removeSameOrderableTwiceTest(){
        OrderableDatabase db = new OrderableDatabaseStub();
        db.removeOrderable("oreo");
        db.removeOrderable("oreo");
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();
        assertEquals(1, ords.size());
        ArrayList<String> names = new ArrayList<>();
        for(Orderable ord: ords){
            names.add(ord.getName());
        }
        assertTrue(names.contains("cheeto"));
        assertFalse(names.contains("oreo"));
    }


    @Test
    void addRemoveSequenceTest(){
        OrderableDatabase db = new OrderableDatabaseStub();
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
        assertEquals(5, ords.size());
        assertTrue(names.contains("oreo"));
        assertTrue(names.contains("cheeto"));
        assertTrue(names.contains("cheese"));
        assertTrue(names.contains("turkey"));
        assertTrue(names.contains("carrot"));

        Orderable ord = db.getOrderable("turkey");
        assertEquals(5.5F, ord.getPrice());
        assertEquals(4, ord.getShelfLife());
    }

    @Test
    void replaceOrderableNewPriceTest(){
        Orderable oreo = new Orderable("oreo", 2.0F, 12);
        OrderableDatabase db = new OrderableDatabaseStub();
        db.replaceOrderable(oreo);
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();
        assertEquals(2, ords.size());
        ArrayList<String> names = new ArrayList<>();
        for(Orderable ord: ords){
            names.add(ord.getName());
        }
        assertTrue(names.contains("cheeto"));
        assertTrue(names.contains("oreo"));
        Orderable ord = db.getOrderable("oreo");
        assertEquals(2.0F, ord.getPrice());
        assertEquals(12, ord.getShelfLife());
    }

    @Test
    void replaceOrderableNewShelfLifeTest(){
        Orderable oreo = new Orderable("oreo", 3.99F, 15);
        OrderableDatabase db = new OrderableDatabaseStub();
        db.replaceOrderable(oreo);
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();
        assertEquals(2, ords.size());
        ArrayList<String> names = new ArrayList<>();
        for(Orderable ord: ords){
            names.add(ord.getName());
        }
        assertTrue(names.contains("cheeto"));
        assertTrue(names.contains("oreo"));
        Orderable ord = db.getOrderable("oreo");
        assertEquals(3.99F, ord.getPrice());
        assertEquals(15, ord.getShelfLife());
    }

    @Test
    void replaceNonexistentOrderableTest(){
        Orderable cereal = new Orderable("cereal", 3.99F, 15);
        OrderableDatabase db = new OrderableDatabaseStub();
        db.replaceOrderable(cereal);
        ArrayList<Orderable> ords = (ArrayList<Orderable>) db.getOrderableList();
        assertEquals(3, ords.size());
        ArrayList<String> names = new ArrayList<>();
        for(Orderable ord: ords){
            names.add(ord.getName());
        }
        assertTrue(names.contains("cheeto"));
        assertTrue(names.contains("oreo"));
        assertTrue(names.contains("cereal"));
        Orderable ord = db.getOrderable("cereal");
        assertEquals(3.99F, ord.getPrice());
        assertEquals(15, ord.getShelfLife());
    }


}
