package unit;

import org.junit.jupiter.api.Test;
import project.logic.ModifierManagerLogic;
import project.persistence.ModifierDatabase;
import project.persistence.ModifierDatabaseStub;

import static org.junit.jupiter.api.Assertions.*;

public class ModifierManagerLogicTest {
    @Test
    void getModifierTable() {
        ModifierDatabase db = new ModifierDatabaseStub();
        ModifierManagerLogic logic = new ModifierManagerLogic(db);

        assertEquals(2, logic.getModifierTable().length);
    }

    @Test
    void addModifier() {
        ModifierDatabase db = new ModifierDatabaseStub();
        ModifierManagerLogic logic = new ModifierManagerLogic(db);

        // Number of mods before adding
        assertEquals(2, db.getModifierList().size());

        // Should run with no errors
        assertTrue(logic.addModifier("apple", "-90", "2000-01-01", "2022-01-01").isEmpty());
        assertEquals(3, db.getModifierList().size());

        // Error handling

        // already exists
        assertFalse(logic.addModifier("apple", "-90", "2000-01-01", "2022-01-01").isEmpty());

        // empty fields
        assertFalse(logic.addModifier("", "-90", "2000-01-01", "2022-01-01").isEmpty());
        assertFalse(logic.addModifier("apple", "", "2000-01-01", "2022-01-01").isEmpty());
        assertFalse(logic.addModifier("apple", "-90", "", "2022-01-01").isEmpty());
        assertFalse(logic.addModifier("apple", "-90", "2000-01-01", "").isEmpty());

        // modifier out of range
        assertFalse(logic.addModifier("apple", "-1000", "2000-01-01", "2022-01-01").isEmpty());
        assertFalse(logic.addModifier("apple", "1000", "2000-01-01", "2022-01-01").isEmpty());
    }

    @Test
    void removeModifier() {
        ModifierDatabase db = new ModifierDatabaseStub();
        ModifierManagerLogic logic = new ModifierManagerLogic(db);

        // Number of mods before adding
        assertEquals(2, db.getModifierList().size());

        // Should run with no errors
        assertTrue(logic.removeModifier("oreo").isEmpty());
        assertEquals(1, db.getModifierList().size());

        // error handling

        // doens't exist
        assertFalse(logic.removeModifier("oreo").isEmpty());

        // empty field
        assertFalse(logic.removeModifier("").isEmpty());
    }
}
