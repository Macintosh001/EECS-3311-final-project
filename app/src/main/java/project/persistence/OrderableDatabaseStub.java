package project.persistence;

import project.objects.Orderable;

import java.util.ArrayList;
import java.util.List;

public class OrderableDatabaseStub implements OrderableDatabase{
    private ArrayList<Orderable> dbStub;

    public OrderableDatabaseStub(){
        dbStub = this.getTestContent();
    }
    private ArrayList<Orderable> getTestContent(){
        ArrayList<Orderable> fixed = new ArrayList<>();
        Orderable oreo = new Orderable("oreo", 3.99F, 12);
        Orderable cheeto = new Orderable("cheeto", 2.99F, 100);
        fixed.add(oreo);
        fixed.add(cheeto);
        return fixed;
    }


    @Override
    public void addOrderable(Orderable orderable) {
        dbStub.add(orderable);
    }

    @Override
    public void removeOrderable(String name) {
        Orderable order = null;
        for(Orderable o : dbStub){
            if(o.getName().compareTo(name) == 0){
                order = o;
                break;
            }
        }
        if(order != null) {
            dbStub.remove(order);
        }
    }

    @Override
    public void replaceOrderable(Orderable orderable) {
        this.removeOrderable(orderable.getName());
        this.addOrderable(orderable);
    }

    @Override
    public Orderable getOrderable(String name) {
        for(Orderable o: dbStub){
            if(o.getName().compareTo(name) == 0){
                return o;
            }
        }
        return null;
    }

    @Override
    public List<Orderable> getOrderableList() {
        return dbStub;
    }
}
