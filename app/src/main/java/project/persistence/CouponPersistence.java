package project.persistence;

import project.objects.Coupon;
import project.objects.Orderable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to interact with the coupon table of the mySQL database.
 * Storage is persistent. Interact with this class through the interface CouponDatabase.
 */
public class CouponPersistence implements CouponDatabase{
    private DatabaseManager db;

    /**
     * Constructor: initialize fields.
     * Check if a mySQL database exists; if not, build one on the user's local host server with the
     * databaseManager field. Force the current version of the database
     * for those with missing tables.
     * This way, a user is guaranteed to have a database that supports the operations in this class
     * before these operations are invoked; this design avoids errors of missing tables or databases.
     */
    public CouponPersistence(String username, String password) throws SQLException {
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
     * Add coupon to the coupon table of the database by first converting the
     * coupon object to an SQL appropriate string, then executing SQL commands wih this string
     * @param coupon to be added
     */
    @Override
    public void addCoupon(Coupon coupon) {
        //Convert coupon object to a string usable in the sql add statement
        String values = this.getSQLCouponString(coupon);
        try {
            Statement statement = this.db.exportStatement();
            //add coupom to db using its SQL value string
            statement.execute("insert into coupon values " + values);
            this.db.terminate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Delete a coupon with the given code from the coupon table in the database.
     * @param code a coupon code of the coupon to be removed
     */
    @Override
    public void removeCoupon(String code) {
        try {
            Statement statement = this.db.exportStatement();
            statement.execute("delete from coupon where code ="+ "'"+code+"'");
            this.db.terminate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Update a coupon in storage by deleting it from the coupon table and adding
     *  a new coupon with the same code but updated information to the table.
     *   If no coupon with the same code as the input exists in storage, a new coupon will be added to storage.
     *  @param coupon updated coupon object
     */
    @Override
    public void replaceCoupon(Coupon coupon) {
        this.removeCoupon(coupon.getCode());
        this.addCoupon(coupon);
    }

    /**
     * Retrieve a coupon with the given code from the database. Build and return a coupon object
     * that is equivalent to the retrieved database entity.
     * Database unchanged by this method
     * @param code code of the retrieved coupon
     * @return a coupon object representing the coupon entity in the database.
     */
    @Override
    public Coupon getCoupon(String code) {
        try{
            Statement statement = db.exportStatement();
            //get the coupon from the coupon table
            ResultSet res = statement.executeQuery("select * from coupon where code = " + "'" + code+ "'");
            //Convert the retrieved result set into a usable coupon object
            Coupon coup = extractCouponFromResultSet(res);
            db.terminate();
            return coup;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns a list of coupons representing the current content of the coupon table in the database
     * @return a list of coupons equivalent to the data in the coupon table.
     */
    @Override
    public List<Coupon> getCouponList() {
        ArrayList<Coupon> coups = new ArrayList<>();
        try{
            Statement statement = db.exportStatement();
            //Query coupon table for the set of all coupon codes
            ResultSet res = statement.executeQuery("select code from coupon");
            //Convert the result set of the query into a list of coupon objects with the same data as the coupons in the database
            coups = this.extractCouponListFromResultSet(res);
            db.terminate();
        }catch(SQLException e){
            e.printStackTrace();
        }

        return coups;
    }

    /**
     * Convert coupon object into a string of its field values in a form
     * useful for JDBC SQL statements.
     * Used to convert a coupon into a form in which it is addable to the coupon table of the database.
     * Helper method for addCoupon()
     * @param coupon a coupon object
     * @return a string of the field values for the coupon, that is addable to the coupon table in the database
     */
    private String getSQLCouponString(Coupon coupon){
        return "(" + "'" + coupon.getCode() + "'" + ", "
                + "'" + coupon.getPercentOff().toString() + "'" + ")";
    }

    /**
     *  Takes a ResultSet object that was created from querying a single coupon entity from the coupon table,
     *  and converts this result set into a coupon object that stores equivlent information.
     *  This is useful in the getCoupon method, which will return the result of its call to this method.
     * @param res a resultSet representing a single coupon having been queried from the coupon table
     * @return a coupon object representing the result of querying a single row of the coupon table.
     */
    private Coupon extractCouponFromResultSet(ResultSet res){
        String code;
        Float percentOff;
        try{
            if(res != null){
                if(res.next()){
                    code = res.getString("code");
                    percentOff = res.getFloat("percent_off");
                    return new Coupon(code, percentOff);
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
     * given a resultset representing a query of coupon codes from the coupon table,
     * this method uses the getCoupon method to create a set of coupon objects with these coupon codes,
     * that represent a set of coupons currently in the database.
     * This is useful in the getCouponList method, which returns the result of its call to this method
     * @param res a resultSet representing a list of queried codes from the coupon table
     * @return a list of coupon objects that a) are currently present in the database and b) have the same codes as those stored in the argument ResultSet
     *
     */
    private ArrayList<Coupon> extractCouponListFromResultSet(ResultSet res){
        ArrayList<Coupon> coups = new ArrayList<>();
        try{
            if(res != null){
                Coupon c;
                while(res.next()){
                    //get a coupon object  representing a coupon from the coupon table
                    // which has a coupon code equivalent to a code from res
                    c = this.getCoupon(res.getString("code"));
                    coups.add(c);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return coups;
    }
    @Override
    public void empty(){
        try{
            Statement statement = db.exportStatement();
            statement.execute("delete from coupon");
            db.terminate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
