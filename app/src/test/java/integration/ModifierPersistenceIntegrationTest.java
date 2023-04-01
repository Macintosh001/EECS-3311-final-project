package integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.objects.Modifier;
import project.objects.RestockTask;
import project.persistence.ModifierDatabase;
import project.persistence.ModifierDatabaseStub;
import project.persistence.ModifierPersistence;
import project.persistence.RestockTaskPersistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModifierPersistenceIntegrationTest {
    private ModifierDatabase db;
    private final String username = "root";
    private final String password = "root1234";

    @BeforeEach
    public void initialize(){
        try{
            db = new ModifierPersistence(username, password);
            db.empty();
            Date date1 = new Date(1);
            Date date2 = new Date(1000000000);
            Date date3 = new Date();
            db.addModifier(new Modifier("oreo", 0.12F, date1, date2));
            db.addModifier(new Modifier("cheetos", 0.1F, date2, date3));
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
    void getModifierTest(){
        Modifier m = this.db.getModifier("oreo");
        assertEquals("oreo", m.getName());
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
        Modifier mod2 = new Modifier("grapes", 0.7F, date2, date3);
        this.db.addModifier(mod);
        this.db.addModifier(mod2);
        Modifier m = this.db.getModifier("muffin");
        assertEquals(m.getName(), "muffin");
    }

    @Test
    void getModListTest(){
        ArrayList<Modifier> ms = (ArrayList<Modifier>) this.db.getModifierList();
        ArrayList<Float> mods = new ArrayList<>();
        for(Modifier m : ms){
            mods.add(m.getModifier());
        }
        assertTrue(mods.contains(0.12F));
        assertTrue(mods.contains(0.1F));
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
