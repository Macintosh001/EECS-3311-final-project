package project.persistence;

import project.objects.Orderable;
import project.objects.RestockTask;

import java.util.ArrayList;
import java.util.List;

public class RestockTaskDatabaseStub implements RestockTaskDatabase{
   private ArrayList<RestockTask> dbStub;

   public RestockTaskDatabaseStub(){
       this.dbStub = this.getTestData();
   }

    /**
     * Get fixed starting data for stub
     * @return arrayList of starting data
     */
   private ArrayList<RestockTask> getTestData(){
       ArrayList<RestockTask> restockTasks = new ArrayList<>();
       restockTasks.add(new RestockTask("oreo", 5, 20));
       restockTasks.add(new RestockTask("cheeto", 3, 15));
       return restockTasks;
   }

    /**
     * get a task from database
     * @param name name of task retrieved
     * @return task with the given name, or null if no such task exists
     */
    @Override
    public RestockTask getRestockTask(String name) {
        for(RestockTask r : dbStub){
            if(r.getName().compareTo(name) == 0){
                return r;
            }
        }
        return null;
    }

    /**
     * return a complete set of tasks currently in the database
     */
    @Override
    public List<RestockTask> getRestockTaskList() {
        return dbStub;
    }

    /**
     * remove a task from database
     * @param name of task to be removed
     */
    @Override
    public void removeRestockTask(String name) {
        RestockTask restock = null;
        for(RestockTask r : dbStub){
            if(r.getName().compareTo(name) == 0){
                restock = r;
                break;
            }
        }
        if(restock != null) {
            dbStub.remove(restock);
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
     * @param mod a task to be added
     */
    @Override
    public void addRestockTask(RestockTask res) {dbStub.add(res);}

    /**
     * make the database empty
     */
    @Override
    public void empty(){
       this.dbStub = new ArrayList<>();
    }
}
