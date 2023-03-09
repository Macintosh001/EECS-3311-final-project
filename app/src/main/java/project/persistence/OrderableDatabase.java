package project.persistence;

import project.objects.Orderable;

import java.util.List;

/**
 * OrderableDatabase interface for logic classes to interact with. Hides from logic whether
 * it is working with a real database or a stub.
 * Implementers represent persistent or temporary storage with add/update/remove/query operations
 * for orderable objects/entities.
 */
public interface OrderableDatabase {

    /**
     * add an orderable to storage
     * @param orderable to be added
     */
    public void addOrderable(Orderable orderable);

    /**
     * remove an orderable with the given name from storage
     * @param name the unique name of the orderable to be removed
     */
    public void removeOrderable(String name);

    /**
     * Update an orderable in storage by deleting it from storage and adding
     * a new orderable with the same name but updated information to storage.
     *  If no orderable with the same name as the input exists in storage, a new orderable will be added to storage.
     * @param orderable updated orderable object
     */
    public void replaceOrderable(Orderable orderable); //for update

    /**
     * Retrieve an orderable from storage with the given name
     * @param name name of the retrieved orderable
     * @return orderable with the given name
     */
    public Orderable getOrderable(String name);

    /**
     * @return a list of orderables currently in storage
     */
    public List<Orderable> getOrderableList();

    public void empty();
}
