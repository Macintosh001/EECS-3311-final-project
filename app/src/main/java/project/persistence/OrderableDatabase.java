package project.persistence;

import project.objects.Orderable;

import java.util.List;

public interface OrderableDatabase {

    public void addOrderable(Orderable orderable);
    public void removeOrderable(String name);
    public void replaceOrderable(Orderable orderable); //for update
    public Orderable getOrderable(String name);
    public List<Orderable> getOrderableList();

}
