package project.persistence;

import project.objects.Orderable;

import java.util.ArrayList;
import java.util.List;

/**
 * An orderable database stub, an implementor of the orderable database interface, used for simulation of real database functionality
 * during testing. Stub is loaded with fixed starting data so that it will have predictable behavior
 * for testing. Stores temporary orderable objects during runtime.
 */
public class OrderableDatabaseStub implements OrderableDatabase{
    private ArrayList<Orderable> dbStub;

    /**
     * Constructor: load stub with fixed content for predictable starting behavior
     */
    public OrderableDatabaseStub(){
        dbStub = this.getTestContent();
    }

    /**
     * Creates starting data for stub
     * @return an arrayList of coupon objects that represents fixed starting data for predictable behavior during testing
     */
    private ArrayList<Orderable> getTestContent(){
        ArrayList<Orderable> fixed = new ArrayList<>();
        Orderable oreo = new Orderable("oreo", 3.99F, 12);
        Orderable cheeto = new Orderable("cheeto", 2.99F, 100);
        fixed.add(oreo);
        fixed.add(cheeto);
        return fixed;
    }


    /**
     *  add an orderable to stub
     * @param orderable to be added
     */
    @Override
    public void addOrderable(Orderable orderable) {
        dbStub.add(orderable);
    }


    /**
     * remove an orderable with the given name from stub
     * @param name the name of the orderable to be removed
     */
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

    /**
     Update an orderable in storage by deleting it from storage and adding
     a new orderable with the same name as the deleted object but updated information.
     If no orderable with the same name as the input exists in storage, a new orderable will be added to storage.
     * @param orderable updated orderable object
     */
    @Override
    public void replaceOrderable(Orderable orderable) {
        this.removeOrderable(orderable.getName());
        this.addOrderable(orderable);
    }

    /**
     * Retrieve an orderable from storage with the given name
     * @param name name of the retrieved orderable
     * @return orderable with the given name
     */
    @Override
    public Orderable getOrderable(String name) {
        for(Orderable o: dbStub){
            if(o.getName().compareTo(name) == 0){
                return o;
            }
        }
        return null;
    }

    /**
     * @return a list of orderables currently in storage
     */
    @Override
    public List<Orderable> getOrderableList() {
        return dbStub;
    }
}
