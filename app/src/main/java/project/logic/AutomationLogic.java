package project.logic;

import project.logic.validation.QuantityValidator;
import project.objects.*;
import project.persistence.OrderableDatabase;
import project.persistence.ProductDatabase;
import project.persistence.RestockTaskDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutomationLogic {
    private RestockTaskDatabase taskDatabase;
    private ProductDatabase productDatabase;
    private OrderableDatabase orderableDatabase;
    private OrderLogic orderLogic;

    public AutomationLogic(RestockTaskDatabase taskDatabase, ProductDatabase productDatabase, OrderableDatabase orderableDatabase, OrderLogic orderLogic) {
        this.taskDatabase = taskDatabase;
        this.productDatabase = productDatabase;
        this.orderableDatabase = orderableDatabase;
        this.orderLogic = orderLogic;
    }

    public String[][] getRestockTaskTable() {
        return TableEntryGenerator.genRestockTaskTableEntries(taskDatabase.getRestockTaskList());
    }

    /**
     * Take the raw UI inputs and add a new automation task to the system if the inputs are valid
     * @param name
     * @param minQuantity
     * @param restockAmount
     * @return the list of errors, empty list if there were no errors
     */
    public List<ErrorMsg> addRestockTask(String name, String minQuantity, String restockAmount) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        Integer oMinQuantity = null;
        Integer oRestockAmount = null;

        // validate name
        if (name.equals("")) {
            errorMsgs.add(new ErrorMsg("Name cannot be empty!"));
            return errorMsgs;
        }

        // we can't add a restock task if there is already one for that orderable
        for (RestockTask t: taskDatabase.getRestockTaskList()) {
            if (t.getName().equals(name)) {
                errorMsgs.add(new ErrorMsg("There is already a task for '" + name + "'!"));
                return errorMsgs;
            }
        }

        // we can't add a restock task if there is no orderable that exists to restock
        boolean orderableExists = false;
        for (Orderable o: orderableDatabase.getOrderableList()) {
            if (o.getName().equals(name)) {
                orderableExists = true;
                break;
            }
        }
        if (!orderableExists) {
            errorMsgs.add(new ErrorMsg("Nothing called '" + name + "' exists that you can make a task for!"));
        }

        // validate minQuantity and restockAmount
        if (minQuantity.equals("") || restockAmount.equals("")) {
            errorMsgs.add(new ErrorMsg("Quantity cannot be empty!"));
            return errorMsgs;
        }

        QuantityValidator validator = new QuantityValidator();
        Result<Integer, List<ErrorMsg>> minQuantityResult = validator.validate(minQuantity);
        Result<Integer, List<ErrorMsg>> restockAmountResult = validator.validate(restockAmount);
        if (minQuantityResult.getError() != null) {
            return minQuantityResult.getError();
        }
        if (restockAmountResult.getError() != null) {
            return restockAmountResult.getError();
        }
        oMinQuantity = minQuantityResult.getResult();
        oRestockAmount = restockAmountResult.getResult();

        // With the inputs validated, we can now add the automation task to the database
        taskDatabase.addRestockTask(new RestockTask(name, oMinQuantity, oRestockAmount));
        return errorMsgs;
    }

    /**
     * Update an existing restock task
     * @param name
     * @param minQuantity
     * @param restockAmount
     * @return error messages or an empty list if the operation succeeded.
     */
    public List<ErrorMsg> updateRestockTask(String name, String minQuantity, String restockAmount) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        Integer oMinQuantity = null;
        Integer oRestockAmount = null;

        // validate name
        if (name.equals("")) {
            errorMsgs.add(new ErrorMsg("Name cannot be empty!"));
            return errorMsgs;
        }

        // we can't update a restock task if it doesn't exist
        boolean taskExists = false;
        for (RestockTask t: taskDatabase.getRestockTaskList()) {
            if (t.getName().equals(name)) {
                taskExists = true;
                break;
            }
        }
        if (!taskExists) {
            errorMsgs.add(new ErrorMsg("You can't update '" + name + "' because it doesn't exist!"));
        }

        // validate minQuantity and restockAmount
        QuantityValidator validator = new QuantityValidator();

        if (!minQuantity.equals("")) {
            Result<Integer, List<ErrorMsg>> minQuantityResult = validator.validate(minQuantity);
            if (minQuantityResult.getError() != null) {
                return minQuantityResult.getError();
            }
            oMinQuantity = minQuantityResult.getResult();
        }

        if (!restockAmount.equals("")) {
            Result<Integer, List<ErrorMsg>> restockAmountResult = validator.validate(restockAmount);
            if (restockAmountResult.getError() != null) {
                return restockAmountResult.getError();
            }
            oRestockAmount = restockAmountResult.getResult();
        }

        // With the inputs validated, we can now update the automation task to the database
        RestockTask oldTask = taskDatabase.getRestockTask(name);

        if (oMinQuantity == null) {
            oMinQuantity = oldTask.getMinQuantity();
        }
        if (oRestockAmount == null) {
            oRestockAmount = oldTask.getRestockAmount();
        }

        taskDatabase.addRestockTask(new RestockTask(name, oMinQuantity, oRestockAmount));
        return errorMsgs;
    }

    /**
     * Take the raw UI input and remove an automation task if it exists
     * @param name
     * @return a list of errors that occurred, empty list if there were no errors
     */
    public List<ErrorMsg> removeRestockTask(String name) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        // validate name
        if (name.equals("")) {
            errorMsgs.add(new ErrorMsg("Name cannot be empty!"));
            return errorMsgs;
        }

        // we can't remove a restock task that doesn't exist
        boolean taskExists = false;
        for (RestockTask t: taskDatabase.getRestockTaskList()) {
            if (t.getName().equals(name)) {
                taskExists = true;
            }
        }
        if (!taskExists) {
            errorMsgs.add(new ErrorMsg("Can't remove '" + name + "' because it doesn't exist!"));
        }

        // with the input validated, we can now remove the task
        taskDatabase.removeRestockTask(name);

        return  errorMsgs;
    }

    /**
     * This function will run all the automation tasks. It is meant to be called every time a view is changed.
     */
    public void automate() {
        Map<String, Integer> stockTotals = new HashMap<>();

        // the only automation task right now is restock, so that's what we do first

        // first we need the total about of stock for each orderable. We only want to track orderables that are still
        // in the orderable database, so we have to initialize all the orderables to zero, the count up the stock
        for (Orderable o: orderableDatabase.getOrderableList()) {
            stockTotals.put(o.getName(), 0);
        }
        for (Product p: productDatabase.getProductList()) {
            if (stockTotals.containsKey(p.getName())) {
                stockTotals.put(p.getName(), stockTotals.get(p.getName()) + p.getQuantity());
            }
        }

        // next we cycle through the restock tasks and perform a restock when needed
        for (RestockTask t: taskDatabase.getRestockTaskList()) {
            // If the key isn't in the stock totals, it means that this orderable isn't in the database and thus
            // cannot be reorders. so, we skip it
            if (!stockTotals.containsKey(t.getName())) {
                continue;
            }

            // We only need to order the restock amount when there stock is below the specified minimum quantity
            if (stockTotals.get(t.getName()) < t.getMinQuantity()) {
                orderLogic.placeOrder(t.getName(), t.getRestockAmount().toString());
            }
        }
    }
}
