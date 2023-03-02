import org.junit.jupiter.api.Test;
import project.logic.StockCheckingLogic;
import project.objects.Product;
import project.persistence.Database;
import project.persistence.DatabaseStub;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockCheckingLogicTest {
    @Test
    void getProductList() {
        Database db = new DatabaseStub();
        StockCheckingLogic logic = new StockCheckingLogic(db);

        db.addProduct(new Product(0, "Chips", 100, 1.99f, new Date(0)));
        db.addProduct(new Product(1, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(2, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(3, "Apples", 400, 1.99f, new Date(3)));
        db.addProduct(new Product(4, "Chips", 200, 3.99f, new Date(4)));
        db.addProduct(new Product(5, "Cheese", 10, 4.99f, new Date(5)));

        assertEquals(6, logic.getProductList().length);
    }

    @Test
    void getFilteredList() {
        // Set up the stub database.
        Database db = new DatabaseStub();
        StockCheckingLogic logic = new StockCheckingLogic(db);

        db.addProduct(new Product(0, "Chips", 100, 1.99f, new Date(0)));
        db.addProduct(new Product(1, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(2, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(3, "Apples", 400, 1.99f, new Date(3)));
        db.addProduct(new Product(4, "Chips", 200, 3.99f, new Date(4)));
        db.addProduct(new Product(5, "Cheese", 10, 4.99f, new Date(5)));

        // Test the filter on quantities
        assertEquals(3, logic.getFilteredList("", "",
                "", "101",
                "", "").getResult().length);

        // Test the filter on prices
        assertEquals(2, logic.getFilteredList("1.0", "2.0",
                "", "",
                "", "").getResult().length);

        // Test invalid inputs
        // There should be one error for each incorrect entry
        assertEquals(6, logic.getFilteredList("x", "x",
                "x", "x",
                "x", "x").getError().size());
    }
}
