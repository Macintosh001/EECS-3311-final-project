package integration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.logic.ModifierManagerLogic;
import project.objects.Modifier;
import project.persistence.*;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class ModifierLogicIntegrationTest {

    private ModifierDatabase db;
    private final String username = "root";
    private final String password = "root1234";
    ModifierManagerLogic logic;

    @BeforeEach
    public void initialize(){
        try{
            db = new ModifierPersistence(username, password);
            logic = new ModifierManagerLogic(db);

            db.empty();

            Date date1 = new Date();
            Instant now = Instant.now();
            Instant yesterday = now.minus(1, ChronoUnit.DAYS);
            Date date2 = Date.from(yesterday);
            Date tomorrow = new Date(date1.getTime() + (1000 * 60 * 60 * 24));

            db.addModifier(new Modifier("oreo", 0.12F, date2, tomorrow));
            db.addModifier(new Modifier("cheeto", 0.1F, date2, tomorrow));
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
    void getModList(){
        assertEquals(2, logic.getModifierTable().length);
    }

    @Test
    void getEmptyModList(){
        db.empty();
        assertEquals(0, logic.getModifierTable().length);
    }

    @Test
    void addModTest(){
        LocalDateTime ldt = LocalDateTime.now();
        String formattedDateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(ldt);
        assertTrue(logic.addModifier("candy", String.valueOf(0.2F), formattedDateStr, formattedDateStr).isEmpty());
        assertEquals(3, logic.getModifierTable().length);
        //errors
        assertFalse(logic.addModifier("", String.valueOf(0.2F), formattedDateStr, formattedDateStr).isEmpty());
        assertFalse(logic.addModifier("turkey", "", formattedDateStr, formattedDateStr).isEmpty());
        assertFalse(logic.addModifier("turkey", String.valueOf(0.2F), (new Date()).toString(), formattedDateStr).isEmpty());
        assertFalse(logic.addModifier("oreo", String.valueOf(0.2F), formattedDateStr, formattedDateStr).isEmpty());
        assertEquals(3, logic.getModifierTable().length);
    }


    @Test
    void removeTest(){
        assertTrue(logic.removeModifier("oreo").isEmpty());
        assertEquals(1, logic.getModifierTable().length);
    }

    @Test
    void removeNonExistentTest(){
        assertFalse(logic.removeModifier("no").isEmpty());
        assertEquals(2, logic.getModifierTable().length);
    }
}
