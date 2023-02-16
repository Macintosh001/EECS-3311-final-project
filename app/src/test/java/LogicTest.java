import project.logic.ILogic;
import project.logic.Logic;
import project.objects.ErrorMsg;
import project.persistence.Database;
import project.persistence.DatabaseStub;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogicTest {
    Database db = new DatabaseStub();
    ILogic logic = new Logic(db, 0);

    @org.junit.jupiter.api.Test
    void addProduct() {
        // No Errors
        List<ErrorMsg> actualErrors = logic.addProduct("Chips", "10", "0.99", "2000-01-01");
        assertEquals(actualErrors.size(), 0);

        // All Blank
        actualErrors = logic.addProduct("", "", "", "");
        assertEquals(actualErrors.size(), 4);

        // Negative Quantity
        actualErrors = logic.addProduct("Chips", "-10", "0.99", "2000-01-01");
        assertEquals(actualErrors.size(), 1);

        // Negative Price
        actualErrors = logic.addProduct("Chips", "10", "-0.99", "2000-01-01");
        assertEquals(actualErrors.size(), 1);
    }

    @org.junit.jupiter.api.Test
    void removeProduct() {
    }

    @org.junit.jupiter.api.Test
    void getProductList() {
    }
}