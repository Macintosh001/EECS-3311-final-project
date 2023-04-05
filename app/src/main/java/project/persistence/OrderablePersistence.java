package project.persistence;

import project.objects.Orderable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to interact with the orderable table of the mySQL database.
 * Storage is persistent. Interact with this class through the interface OrderableDatabase.
 */
public class OrderablePersistence implements OrderableDatabase{
    private DatabaseManager db;

    /**
     * Constructor: initialize fields.
     * Check if a mySQL database exists; if not, build one on the user's local host server with the
     * databaseManager field. Force the current version of the database
     * for those with missing tables.
     * This way, a user is guaranteed to have a database that supports the operations in this class
     * before these operations are invoked; this design avoids errors of missing tables or databases.
     */
    public OrderablePersistence(String username, String password) throws SQLException {
        this.db = new DatabaseManager(username, password);

        //test connection, throw exception if access denied
        this.db.connectToServer();
        this.db.terminate();

        if(!db.databaseExists()) {
            db.createDB();
        }
        db.forceCurrentVersion();
    }


    /**
     * Add orderable to the orderable table of the database by first converting the
     * orderable object to an SQL appropriate string, then executing SQL commands wih this string
     * @param orderable to be added
     */
    @Override
    public void addOrderable(Orderable orderable) {
        //convert to SQL value string
        String values = this.getSQLOrderableString(orderable);
        try {
            Statement statement = this.db.exportStatement();
            statement.execute("insert into orderable values " + values);
            this.db.terminate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Delete an orderable with the given name from the orderable table in the database.
     * @param name the unique name of the orderable to be removed
     */
    @Override
    public void removeOrderable(String name) {
        try {
            Statement statement = this.db.exportStatement();
            statement.execute("delete from orderable where name ="+ "'"+name+"'");
            this.db.terminate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Update an orderable in storage by deleting it from the orderable table and adding
     *  a new orderable with the same name but updated information to the table.
     *   If no orderable with the same name as the input exists in storage, a new orderable will be added to storage.
     *  @param orderable updated orderable object
     */
    @Override
    public void replaceOrderable(Orderable orderable) {
        this.removeOrderable(orderable.getName());
        this.addOrderable(orderable);
    }

    /**
     * Retrieve an orderable with the given name from the database. Build and return an orderable object
     * that is equivalent to the retrieved database entity.
     * Database unchanged by this method
     * @param name name of the retrieved orderable
     * @return a orderable object representing the coupon orderable in the database.
     */
    @Override
    public Orderable getOrderable(String name) {
        try{
            Statement statement = db.exportStatement();
            ResultSet res = statement.executeQuery("select * from orderable where name = " + "'" + name+ "'");
            //get an orderable object from the query results
            Orderable ord = extractOrderableFromResultSet(res);
            db.terminate();
            return ord;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * Returns a list of orderables representing the current content of the orderable table in the database
     * @return a list of orderables equivalent to the data in the orderable table.
     */
    @Override
    public List<Orderable> getOrderableList() {
        ArrayList<Orderable> ords = new ArrayList<>();
        try{
            Statement statement = db.exportStatement();
            ResultSet res = statement.executeQuery("select name from orderable");
            ords = this.extractOrderableListFromResultSet(res);
            db.terminate();
        }catch(SQLException e){
            e.printStackTrace();
        }

        return ords;
    }


    /**
     * Convert orderable object into a string of its field values in a form
     * useful for JDBC SQL statements.
     * Used to convert an orderable into a form in which it is addable to the orderable table of the database.
     * Helper method for addOrderable()
     * @param orderable a orderable object
     * @return a string of the field values for the orderable, that is addable to the orderable table in the database
     */
    private String getSQLOrderableString(Orderable orderable){
        String price = orderable.getPrice().toString();
        String shelfLife = orderable.getShelfLife().toString();
        return "(" + "'" + orderable.getName() + "'"
                + ", " + "'" + price + "'"
                + ", " + "'" + shelfLife + "'" + ")";
    }


    /**
     *  Takes a ResultSet object that was created from querying a single orderable entity from the orderable table,
     *  and converts this result set into an orderable object that stores equivalent information.
     *  This is useful in the getCoupon method, which will return the result of its call to this method.
     * @param res a resultSet representing a single orderable having been queried from the orderable table
     * @return am orderable object representing the result of querying a single row of the coupon table.
     */
    private Orderable extractOrderableFromResultSet(ResultSet res) {
        String name;
        Float price;
        Integer shelfLife;

        try{
            if(res != null){
                if(res.next()){
                    name = res.getString("name");
                    price = res.getFloat("price");
                    shelfLife = res.getInt("shelf_life");
                    return new Orderable(name, price, shelfLife);
                }
                else{
                    return null;
                }
            }
            else {
                return null;
            }
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * given a resultset representing a query of oderable names from the coupon table,
     * this method uses the getOrderable method to create a set of orderable objects with these names,
     * that represent a set of orderables currently in the database.
     * This is useful in the getOrderableList method, which returns the result of its call to this method
     * @param res a resultSet representing a list of queried names from the orderable table
     * @return a list of orderable objects that a) are currently present in the database and b) have the same names as those stored in the argument ResultSet
     *
     */
    private ArrayList<Orderable> extractOrderableListFromResultSet(ResultSet res){
        ArrayList<Orderable> ords = new ArrayList<>();
        try{
            if(res != null){
                Orderable o;
                while(res.next()){
                    o = this.getOrderable(res.getString("name"));
                    ords.add(o);
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return ords;
    }

    @Override
    public void empty(){
        try{
            Statement statement = db.exportStatement();
            statement.execute("delete from orderable");
            db.terminate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
