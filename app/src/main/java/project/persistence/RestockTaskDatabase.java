package project.persistence;
import project.objects.*;
import java.util.List;

public interface RestockTaskDatabase {

    /**
     * get a task from database
     * @param name name of task retrieved
     * @return task with the given name, or null if no such task exists
     */
    RestockTask getRestockTask(String name);

    /**
     * return a complete set of tasks currently in the database
     */
    List<RestockTask> getRestockTaskList();

    /**
     * remove a task from database
     * @param name of task to be removed
     */
    void removeRestockTask(String name);

    /**
     * Update RestockTask
     * @param res updated task
     */
    void replaceRestockTask(RestockTask res);

    /**
     * Add task to db
     * @param res a task to be added
     */
    void addRestockTask(RestockTask res);

    /**
     * make the database empty
     */
    void empty();


}
