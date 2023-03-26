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

   private ArrayList<RestockTask> getTestData(){
       ArrayList<RestockTask> restockTasks = new ArrayList<>();
       restockTasks.add(new RestockTask("oreo", 5, 20));
       restockTasks.add(new RestockTask("cheetos", 3, 15));
       return restockTasks;
   }

    @Override
    public RestockTask getRestockTask(String name) {
        for(RestockTask r : dbStub){
            if(r.getName().compareTo(name) == 0){
                return r;
            }
        }
        return null;
    }

    @Override
    public List<RestockTask> getRestockTaskList() {
        return dbStub;
    }

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

    @Override
    public void replaceRestockTask(RestockTask res) {
        this.removeRestockTask(res.getName());
        this.addRestockTask(res);
    }

    @Override
    public void addRestockTask(RestockTask res) {dbStub.add(res);}
}
