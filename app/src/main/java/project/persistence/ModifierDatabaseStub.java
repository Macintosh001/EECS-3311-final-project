package project.persistence;

import project.objects.Coupon;
import project.objects.Modifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Non-persistent storage for modifier objects
 */
public class ModifierDatabaseStub implements ModifierDatabase{
    private ArrayList<Modifier> dbStub;

    public ModifierDatabaseStub(){
        this.dbStub = this.getTestContent();
    }

    /**
     * Create an array of modifiers for use in testing or initialization of the db stub
     * @return an arrayList of modifiers
     */
    private ArrayList<Modifier> getTestContent() {
        ArrayList<Modifier> ret = new ArrayList<>();
        Date dateFrom = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30));
        Date dateTo = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30));
        Modifier mod = new Modifier("oreo", 0.9F, dateFrom, dateTo);
        Modifier mod2 = new Modifier("cheeto", -0.9F, dateFrom, dateTo);
        ret.add(mod);
        ret.add(mod2);
        return ret;
    }

    /**
     * Add modifier to db stub
     * @param mod a modifier
     */
    public void addModifier(Modifier mod){
        dbStub.add(mod);
    }

    /**
     * remove a modifier from database stub
     * @param name of mod to be removed
     */
    public void removeModifier(String name){
        Modifier mod = null;
        for(Modifier m : dbStub){
            if(m.getName().compareTo(name) == 0){
                mod = m;
                break;
            }
        }
        if(mod != null) {
            dbStub.remove(mod);
        }
    }

    /**
     * get a modifier from the database
     * @param name name of modifier retrieved
     * @return modifier with the given name, or null if no such mod exists
     */
    public Modifier getModifier(String name){
        for(Modifier mod: dbStub){
            if(mod.getName().compareTo(name) == 0){
                return mod;
            }
        }
        return null;
    }


    /**
     * @return a complete set of modifiers currently in the database stub
     */
    public List<Modifier> getModifierList(){
        return this.dbStub;
    }

    /**
     * make the database empty
     */
    public void empty(){
        this.dbStub = new ArrayList<>();
    }
}
