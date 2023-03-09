package unit;


import org.junit.jupiter.api.Test;
import project.logic.StockManagingLogic;
import project.objects.Product;
import project.persistence.ProductDatabase;
import project.persistence.ProductDatabaseStub;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class StockManagingLogicTest {
    @Test
    void removeProduct() {
        ProductDatabase db = new ProductDatabaseStub();
        StockManagingLogic logic = new StockManagingLogic(db);

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
}