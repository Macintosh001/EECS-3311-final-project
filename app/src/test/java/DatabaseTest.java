import org.junit.jupiter.api.Test;
import project.objects.Product;
import project.persistence.Database;
import project.persistence.Persistence;

import java.util.Date;

public class DatabaseTest {
    Database db = new Persistence();

    @Test
    void emptyDatabase() {
        System.out.println(db.getProductList());
    }
}
