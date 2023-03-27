package project.persistence;

import project.objects.Product;
import project.objects.RestockTask;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RestockTaskPersistence implements RestockTaskDatabase{
    private DatabaseManager db;

    public RestockTaskPersistence(String username, String password) throws SQLException {
        this.db = new DatabaseManager(username, password);

        //test connection, throw exception if fails
        this.db.connectToServer();
        this.db.terminate();

        //build database if it doesn't exist
        if(!db.databaseExists()) {
            db.createDB();
        }
        db.forceCurrentVersion();
    }

    /**
     * get a task from database
     * @param name name of task retrieved
     * @return task with the given name, or null if no such task exists
     */
    @Override
    public RestockTask getRestockTask(String name) {
        try{
            Statement statement = db.exportStatement();
            ResultSet res = statement.executeQuery("select * from restock_task where name =" + "'" +name+ "'");
            RestockTask r = extractRestockTaskFromResultSet(res);
            db.terminate();
            return r;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * return a complete set of tasks currently in the database
     */
    @Override
    public List<RestockTask> getRestockTaskList() {
        ArrayList<RestockTask> tasks = new ArrayList<>();
        try{
            Statement statement = db.exportStatement();
            ResultSet res = statement.executeQuery("select name from restock_task");
            tasks = this.extractRestockTaskListFromResultSet(res);
            db.terminate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return tasks;
    }


    /**
     * remove a task from database
     * @param name of task to be removed
     */
    @Override
    public void removeRestockTask(String name) {
        try {
            Statement statement = this.db.exportStatement();
            statement.execute("delete from restock_task where name ="+ "'" + name + "'");
            this.db.terminate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Update RestockTask
     * @param res updated task
     */
    @Override
    public void replaceRestockTask(RestockTask res) {
        this.removeRestockTask(res.getName());
        this.addRestockTask(res);
    }

    /**
     * Add task to db
     * @param res a task to be added
     */
    @Override
    public void addRestockTask(RestockTask res) {
        String values = this.getSQLRestockTaskString(res);
        try {
            Statement statement = this.db.exportStatement();
            statement.execute("insert into restock_task values " + values);
            this.db.terminate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Create a string version of a task's fields that has a form useful for adding tasks
     * to the database.
     * @param res a task
     * @return a string representation of the task fields
     */
    private String getSQLRestockTaskString(RestockTask res) {
        String minQuantity = res.getMinQuantity().toString();
        String name = res.getName();
        String restockAmount = res.getRestockAmount().toString();

        String ret = "(" +
                "'" + name + "'" + ", " +
                minQuantity + ", " +
                restockAmount +
                ")";
        return ret;
    }

    /**
     * Given a resulSet representing a single task queried from the database,
     * return an object representation of this task
     * @param res a resultset containing a single task entity
     * @return a task object that is equivalent to the resultSet data
     */
    private RestockTask extractRestockTaskFromResultSet(ResultSet res) {
        String name;
        Integer restockAmount;
        Integer minQuantity;

        try{
            if(res != null){
                if(res.next()){
                    name = res.getString("name");
                    minQuantity = res.getInt("min_quantity");
                    restockAmount = res.getInt("restock_amount");
                    return new RestockTask(name, minQuantity, restockAmount);
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
     * @return a list of all tasks in the database in object form
     */
    private ArrayList<RestockTask> extractRestockTaskListFromResultSet(ResultSet res) {
        ArrayList<RestockTask> taskList= new ArrayList<>();
        try{
            if(res != null){
                while(res.next()){
                    RestockTask r = this.getRestockTask(res.getString("name"));
                    taskList.add(r);
                }
                return taskList;
            }
            return null;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * make the database empty
     */
    @Override
    public void empty() {
        try{
            Statement statement = db.exportStatement();
            statement.execute("delete from restock_task");
            db.terminate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
