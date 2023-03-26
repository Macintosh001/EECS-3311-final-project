package project.persistence;
import project.objects.*;
import java.util.List;

public interface RestockTaskDatabase {
    RestockTask getRestockTask(String name);
    List<RestockTask> getRestockTaskList();

    void removeRestockTask(String name);

    void replaceRestockTask(RestockTask res);

    void addRestockTask(RestockTask res);


}
