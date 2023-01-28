package project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This Class implements database connections and some basic DB functionality needed for an inventory system.
 * It can create the database and relational schema for the inventory system for new users.
 * Note that this class must be instantiated for use, as this was seen as easier than passing
 * Connection objects around static methods. That said, it can be easily modified to use static methods
 * if the need arises.
 */
public class Database {
    private Connection con; //the connection to be made and terminated
    private String url; //the url of the server or database to be connected to
    private String username; //the username of the user connecting
    private String password; //the password of the connection

    public Database(){
        //initialize some connection information
        url = "jdbc:mysql://localhost:3306/Assignment_EECS_3311";
        username = "root";
        password = "379680";
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
        url = "jdbc:mysql://localhost:3306/Assignment_EECS_3311";
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
            statement.execute("create schema Assignment_EECS_3311");
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
        try {
            this.connect();
            Statement statement = con.createStatement();
            statement.execute("create table PRODUCT" +
                    "(BARCODE INT primary key not null," +
                    "NAME VARCHAR(500)," +
                    "PRICE REAL not null check(price >= 0) default(0), " +
                    "QUANTITY INT not null default(0), " +
                    "EXPIRY_DATE DATE)");
            this.terminate();
        } catch(SQLException c) {
            c.printStackTrace();
        }
    }

}
