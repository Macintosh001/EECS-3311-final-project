package unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.objects.Modifier;
import project.persistence.ModifierDatabase;
import project.persistence.ModifierDatabaseStub;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ModifierDatabaseStubTest {
    private ModifierDatabase db;

    @BeforeEach
    public void initialize(){
        db = new ModifierDatabaseStub();
    }

    @Test
    void getModifierTest(){
        Modifier m = this.db.getModifier("oreo");
        assertEquals("oreo", m.getName());
        assertEquals(new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30)), m.getDateFrom());
        assertEquals(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30)), m.getDateTo());

    }

    @Test
    void getNonexistantModifier(){
        Modifier m = this.db.getModifier("not");
        assertNull(m);
    }

    @Test
    void addGetTest(){
        Date date1 = new Date(1);
        Date date2 = new Date(1000000000);
        Date date3 = new Date();
        Modifier mod = new Modifier("muffin", 0.1F, date1, date2);
        Modifier mod2 = new Modifier("cheetos", 0.7F, date2, date3);
        this.db.addModifier(mod);
        this.db.addModifier(mod2);
        Modifier m = this.db.getModifier("muffin");
        assertEquals(m.getName(), "muffin");
        assertEquals(m.getDateFrom(), new Date(1));
        assertEquals(m.getDateTo(), new Date(1000000000));
    }

    @Test
    void getModListTest(){
        ArrayList<Modifier> ms = (ArrayList<Modifier>) this.db.getModifierList();
        ArrayList<Float> mods = new ArrayList<>();
        for(Modifier m : ms){
            mods.add(m.getModifier());
        }
        assertTrue(mods.contains(0.9F));
        assertTrue(mods.contains(-0.9F));
    }

    @Test
    void getEmptyModListTest(){
        db.empty();
        ArrayList<Modifier> ms = (ArrayList<Modifier>) db.getModifierList();
        assertEquals(0, ms.size());
    }


    @Test
    void addModsTest(){
        Date date1 = new Date(1);
        Date date2 = new Date(1000000000);
        Date date3 = new Date();
        db.addModifier(new Modifier("turkey", 0.1F, date1, date2));
        db.addModifier(new Modifier("buns", 0.9F, date2, date3));
        db.addModifier(new Modifier("chicken", 0.8F, date1, date2));
        db.addModifier(new Modifier("egg", 0.7F, date2, date3));
        db.addModifier(new Modifier("candy", 0.6F, date1, date2));
        ArrayList<Modifier> ms = (ArrayList<Modifier>) db.getModifierList();
        assertEquals(7, ms.size());
        assertEquals("turkey", db.getModifier("turkey").getName());
        assertEquals(0.9F, db.getModifier("buns").getModifier());
        assertEquals(0.8F, db.getModifier("chicken").getModifier());
        assertEquals(date2, db.getModifier("egg").getDateFrom());
        assertEquals(date2, db.getModifier("candy").getDateTo());
    }

    @Test
    void addNull(){
        Date date1 = new Date(1);
        Date date2 = new Date(1000000000);
        db.addModifier(null);
        db.addModifier(new Modifier("turkey", 0.1F, date1, date2));
        ArrayList<Modifier> ms = (ArrayList<Modifier>) db.getModifierList();
        assertEquals(4, ms.size());
    }

    @Test
    void removeTest(){
        db.removeModifier("oreo");
        ArrayList<Modifier> ms = (ArrayList<Modifier>) this.db.getModifierList();
        ArrayList<Float> mods = new ArrayList<>();
        for(Modifier m : ms){
            mods.add(m.getModifier());
        }
        assertFalse(mods.contains(0.12F));
    }

    @Test
    void reomveNonexistantModTest(){
        db.removeModifier("no");
        ArrayList<Modifier> ms = (ArrayList<Modifier>) this.db.getModifierList();
        assertEquals(2, ms.size());
    }


}
