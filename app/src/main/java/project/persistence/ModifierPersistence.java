package project.persistence;

import project.objects.Modifier;
import project.objects.Product;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistent storage for modifier objects
 */
public class ModifierPersistence implements ModifierDatabase{
    private DatabaseManager db;

    public ModifierPersistence(String username, String password) throws SQLException {
        this.db = new DatabaseManager(username, password);

        //test connection with given username/password, throw exception if fail
        this.db.connectToServer();
        this.db.terminate();

        if(!db.databaseExists()) {
            db.createDB();
        }
        db.forceCurrentVersion();
    }

    /**
     * Add modifier to db
     * @param mod a modifier
     */
    public void addModifier(Modifier mod){
        String values = this.getSQLModifierString(mod);
        try {
            Statement statement = this.db.exportStatement();
            statement.execute("insert into modifier values " + values);
            this.db.terminate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }


    /**
     * remove a modifier from database
     * @param name of mod to be removed
     */
    public void removeModifier(String name){
        try {
            Statement statement = this.db.exportStatement();
            statement.execute("delete from modifier where name ="+ "'"+name+"'");
            this.db.terminate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * get a modifier from database
     * @param name name of modifier retrieved
     * @return modifier with the given name, or null if no such mod exists
     */
    public Modifier getModifier(String name){
        try{
            Statement statement = db.exportStatement();
            ResultSet res = statement.executeQuery("select * from modifier where name = " + "'"+ name + "'");
            Modifier mod = extractModifierFromResultSet(res);
            db.terminate();
            return mod;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @return a complete set of modifiers currently in the database
     */
    public List<Modifier> getModifierList(){
        ArrayList<Modifier> mods = new ArrayList<>();
        try{
            Statement statement = db.exportStatement();
            ResultSet res = statement.executeQuery("select name from modifier");
            mods = this.extractModifierListFromResultSet(res);
            db.terminate();
        }catch(SQLException e){
            e.printStackTrace();
        }

        return mods;
    }


    /**
     * make the database empty
     */
    public void empty(){
        try{
            Statement statement = db.exportStatement();
            statement.execute("delete from modifier");
            db.terminate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Create a string version of a modifiers fields that has a form useful for adding modifiers
     * to the database.
     * @param mod a modifier
     * @return a string representation of the modifiers fields
     */
    public String getSQLModifierString(Modifier mod) {
        return "(" + "'" + mod.getName() + "', "
                + "'" + mod.getModifier() + "', "
                + "'" + (new java.sql.Date(mod.getDateFrom().getTime())).toString() + "', "
                + "'" + (new java.sql.Date(mod.getDateTo().getTime())).toString() + "'"
                + ")";
    }

    /**
     * Given a resulSet representing a single modifier queried from the database,
     * return an object representation of this modifier
     * @param res a resultset containing a single modifer entity
     * @return a modifier object that is equivalent to the resultSet data
     */
    private Modifier extractModifierFromResultSet(ResultSet res) {
        String name;
        Float modifier;
        Date dateFrom;
        Date dateTo;

        try{
            if(res != null){
                if(res.next()){
                    name = res.getString("name");
                    modifier = res.getFloat("modifier");
                    dateFrom = new Date(res.getDate("date_from").getTime());
                    dateTo = new Date(res.getDate("date_to").getTime());
                    return new Modifier(name, modifier, dateFrom, dateTo);
                }
                else {
                    return null;
                }
            }
            else{
                return null;
            }
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @param res a resultSet of all names in the database
     * @return a list of all modifiers in the database in object form
     */
    private ArrayList<Modifier> extractModifierListFromResultSet(ResultSet res) {
        ArrayList<Modifier> modList = new ArrayList<>();
        try{
            if(res != null){
                while(res.next()){
                    Modifier m = this.getModifier(res.getString("name"));
                    modList.add(m);
                }
                return modList;
            }
            return null;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
