package project.persistence;
import java.sql.*;

/**
 * This Class implements database connections and some basic DB functionality needed for an inventory system.
 * It can create the database and relational schema for the inventory system for new users.
 * Note that this class must be instantiated for use, as this was seen as easier than passing
 * Connection objects around static methods. That said, it can be easily modified to use static methods
 * if the need arises.
 */
public class DatabaseManager {
    private Connection con; //the connection to be made and terminated
    private String url; //the url of the server or database to be connected to
    private String username; //the username of the user connecting
    private String password; //the password of the connection

    public DatabaseManager(String username, String password){
        //initialize some connection information
        url = "jdbc:mysql://localhost:3306/TIM_Assignment_EECS_3311";
        this.username = username;
        this.password = password;
    }

    /**
     * Connect to a server, but not any specific database on that server.
     * Used to create an empty inventory database for new users.
     * Note: may be updated to return flags of success/failure
     * @throws SQLException
     */
    public void connectToServer() throws SQLException{
        url = "jdbc:mysql://localhost:3306/";
        con = DriverManager.getConnection(url, username, password);
    }

    /**
     * connect to the database for the inventory system on a local server.
     * @throws: SQLException
     */
    public void connect(){
        url = "jdbc:mysql://localhost:3306/TIM_Assignment_EECS_3311";
        try {
            con = DriverManager.getConnection(url, username, password);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * End the database connection associated with the instantiation of this class.
     * Should be used every time a database operation is complete to avoid privacy leaks.
     * @pre: a connection is in place
     * @throws: SQLException
     *
     */
    public void terminate(){
        try {
            con.close(); //close database connection
        } catch(SQLException c) {
            c.printStackTrace();
        }
    }

    /**
     * Creates an inventory database on a local server for first-time users of the inventory system.
     * Should be used only once: that is, when the user first uses the inventory system.
     * @pre: the user does not already have a database of the specified name or a schema build on that database.
     * @throws: SQLException
     */
    public void createDB(){
        try {
            this.connectToServer();
            Statement statement = con.createStatement();
            statement.execute("create schema TIM_Assignment_EECS_3311");
            this.terminate();
            this.buildDBSchema();

        } catch(SQLException c) {
            c.printStackTrace();
        }
    }


    /**
     * Initializes the inventory database. That is, creates a working, but empty, inventory system
     * on the database with create table statements. Must be called, or the equivalent actions taken on the mySQL server
     * directly, before anything is added/removed/queried to/from the database.
     * @throws: SQLException
     *
     */
    private void buildDBSchema(){
            createProductTable();
            createCouponTable();
            createOrderableTable();
            createModifierTable();
    }

    /**
     * create the product table in the db
     */
    private void createProductTable(){
        try {
            this.connect();
            Statement statement = con.createStatement();
            statement.execute("create table product" +
                    "(barcode int primary key not null," +
                    "name varchar(500)," +
                    "price real not null check(price >= 0) default(0), " +
                    "quantity int not null default(0), " +
                    "expiry_date date)");
            this.terminate();
        } catch(SQLException c) {
            c.printStackTrace();
        }
    }

    /**
     * create the coupon table in the db
     */
    private void createCouponTable(){
        try {
            this.connect();
            Statement statement = con.createStatement();
            statement.execute("create table coupon"
            +"(code varchar(500) primary key not null,"
            +"percent_off real not null default(0)" +
                    ")");
            this.terminate();
        } catch(SQLException c) {
            c.printStackTrace();
        }
    }

    /**
     * create the orderable table in the db
     */
    private void createOrderableTable(){
        try {
            this.connect();
            Statement statement = con.createStatement();
            statement.execute("create table orderable"
                    +"(name varchar(500) primary key not null,"
                    +"price real not null check(price >= 0) default(0),"
                    +"shelf_life int not null check(shelf_life >= 0) default(0)" +
                    ")");
            this.terminate();
        } catch(SQLException c) {
            c.printStackTrace();
        }
    }

    private void createModifierTable(){
        try {
            this.connect();
            Statement statement = con.createStatement();
            statement.execute("create table modifier"
                    +"(name varchar(500) primary key not null,"
                    +"modifier real not null default(0),"
                    +"date_from date not null,"
                    +"date_to date not null"
                    + ")");
            this.terminate();
        } catch(SQLException c) {
            c.printStackTrace();
        }
    }

    /**
     *
     * @return true if a database with the name of the database to be created exists, false otherwise
     */
    public boolean databaseExists(){
        try {
            this.connectToServer();
            Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery("show databases");
            if(res != null){
                while(res.next()){
                    if(res.getString(1).toLowerCase().compareTo("tim_assignment_eecs_3311") == 0){
                        this.terminate();
                        return true;
                    }
                }
            }
            this.terminate();
            return false;

        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     Add all missing tables to the database. Used to maintain the current version of the db
     */
    public void forceCurrentVersion(){
        if(!tableFound("coupon")){
            createCouponTable();
        }
        if(!tableFound("product")){
            createProductTable();
        }
        if(!tableFound("orderable")){
            createOrderableTable();
        }
        if(!tableFound("modifier")){
            createModifierTable();
        }

    }

    /**
     *
     * @param tableName name of a table
     * @return true if table with tableName exists in db
     */
    private boolean tableFound(String tableName){
        try {
            this.connect();
            Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery("show tables");
            if(res != null){
                while(res.next()){
                    if(res.getString(1).toLowerCase().compareTo(tableName.toLowerCase()) == 0){
                        this.terminate();
                        return true;
                    }
                }
            }
            this.terminate();
            return false;

        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Connect to db and create statement object
     * @return statement object
     * Note: should call terminate() after use of the exported statement is complete.
     */
    public Statement exportStatement(){
        try{
            this.connect();
            return con.createStatement();
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }


}