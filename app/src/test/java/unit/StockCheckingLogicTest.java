package unit;

import org.junit.jupiter.api.Test;
import project.logic.StockCheckingLogic;
import project.objects.Product;
import project.persistence.ModifierDatabase;
import project.persistence.ModifierDatabaseStub;
import project.persistence.ProductDatabase;
import project.persistence.ProductDatabaseStub;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockCheckingLogicTest {
    @Test
    void getProductList() {
        ProductDatabase db = new ProductDatabaseStub();
        ModifierDatabase mdb = new ModifierDatabaseStub();
        StockCheckingLogic logic = new StockCheckingLogic(db, mdb);

        // The stub database initializes with 2 elements.
        assertEquals(2, logic.getProductList().length);
    }

    @Test
    void getFilteredList() {
        // Set up the stub database.
        ProductDatabase db = new ProductDatabaseStub();
        ModifierDatabase mdb = new ModifierDatabaseStub();
        StockCheckingLogic logic = new StockCheckingLogic(db, mdb);

        // Test the filter on quantities
        assertEquals(2, logic.getFilteredList("", "",
                "", "50",
                "", "").getResult().length);

        // Test the filter on prices
        assertEquals(2, logic.getFilteredList("2.0", "3.0",
                "", "",
                "", "").getResult().length);

        // Test invalid inputs
        // There should be one error for each incorrect entry
        assertEquals(6, logic.getFilteredList("x", "x",
                "x", "x",
                "x", "x").getError().size());
    }
}