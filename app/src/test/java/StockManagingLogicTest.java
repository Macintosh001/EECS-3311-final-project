import org.junit.jupiter.api.Test;
import project.logic.StockCheckingLogic;
import project.logic.StockManagingLogic;
import project.objects.Product;
import project.persistence.Database;
import project.persistence.DatabaseStub;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class StockManagingLogicTest {
    @Test
    void removeProduct() {
        Database db = new DatabaseStub();
        StockManagingLogic logic = new StockManagingLogic(db);

        db.addProduct(new Product(0, "Chips", 100, 1.99f, new Date(0)));
        db.addProduct(new Product(1, "Tuna", 200, 0.99f, new Date(1)));
        db.addProduct(new Product(2, "Apples", 100, 2.99f, new Date(2)));
        db.addProduct(new Product(3, "Apples", 400, 1.99f, new Date(3)));
        db.addProduct(new Product(4, "Chips", 200, 3.99f, new Date(4)));
        db.addProduct(new Product(5, "Cheese", 10, 4.99f, new Date(5)));

        // Test that the item was actually removed
        logic.removeProduct("0");
        assertEquals(5, logic.getProductList().length);

        // Test that an error is reported when the item is not in the database
        assertFalse(logic.removeProduct("0").isEmpty());
        assertEquals(5, logic.getProductList().length);

        // Test that an error is reported when the input is invalid
        assertFalse(logic.removeProduct("x").isEmpty());
    }
}
