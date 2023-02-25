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

    public DatabaseManager(){
        //initialize some connection information
        url = "jdbc:mysql://localhost:3306/TIM_Assignment_EECS_3311";
        username = "root";
        password = "root1234";
    }

    /**
     * Connect to a server, but not any specific database on that server.
     * Used to create an empty inventory database for new users.
     * Note: may be updated to return flags of success/failure
     * @throws SQLException
     */
    public void connectToServer(){
        url = "jdbc:mysql://localhost:3306/";
        try {
            con = DriverManager.getConnection(url, username, password);
        }
        catch(Exception e){
            e.printStackTrace();
        }
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
    }

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

    private void createCouponTable(){
        try {
            this.connect();
            Statement statement = con.createStatement();
            statement.execute("create table coupon"
            +"(code varchar(500) primary key not null,"
            +"discount real not null default(0)" +
                    ")");
            this.terminate();
        } catch(SQLException c) {
            c.printStackTrace();
        }
    }

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

    }

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