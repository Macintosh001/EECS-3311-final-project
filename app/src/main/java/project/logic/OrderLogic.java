package project.logic;

import project.logic.validation.PriceValidator;
import project.logic.validation.QuantityValidator;
import project.logic.validation.ShelfLifeValidator;
import project.objects.ErrorMsg;
import project.objects.Orderable;
import project.objects.Product;
import project.objects.Result;
import project.persistence.OrderableDatabase;
import project.persistence.ProductDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OrderLogic {
    ProductDatabase productDB;
    OrderableDatabase orderableDB;
    private Integer nextBarcode;

    public OrderLogic(ProductDatabase productDB, OrderableDatabase orderableDB) {
        this.productDB = productDB;
        this.orderableDB = orderableDB;
        this.nextBarcode = 0;

        for (Product p: productDB.getProductList()) {
            if (p.getBarcode() > nextBarcode) nextBarcode = p.getBarcode();
        }

        nextBarcode++;
    }

    /**
     * Order a single batch of a product to be added to the product database
     * @param name the name of the product being ordered
     * @param quantity how much to order
     * @return a list of error messages, empty if there were none
     */
    public List<ErrorMsg> placeOrder(String name, String quantity) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        // Make sure name isn't empty
        if (name.equals("")) {
            errorMsgs.add(new ErrorMsg("Name cannot be empty!"));
            return errorMsgs;
        }

        // Validate quantity
        if (quantity.equals("")) {
            errorMsgs.add(new ErrorMsg("Quantity cannot be empty!"));
            return errorMsgs;
        }
        QuantityValidator v = new QuantityValidator();
        Result<Integer, List<ErrorMsg>> quantityResult = v.validate(quantity);
        if (quantityResult.getError() != null) {
            return quantityResult.getError();
        }
        Integer oQuantity = quantityResult.getResult();

        // Grab the Orderable from the database, throw error if it doesn't exist
        Orderable orderable = null;
        for (Orderable o: orderableDB.getOrderableList()) {
            if (o.getName().equals(name)) {
                orderable = o;
                break;
            }
        }
        if (orderable == null) {
            errorMsgs.add(new ErrorMsg("Nothing called '" + name + "' can be ordered!"));
            return errorMsgs;
        }


        // Construct the product to be added to the product database
        Product product = new Product(
                nextBarcode,
                name,
                oQuantity,
                orderable.getPrice(),
                new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(orderable.getShelfLife()))
        );
        nextBarcode++;

        // Add it to the database
        productDB.addProduct(product);

        return errorMsgs;
    }

    public List<ErrorMsg> updatePrice(String name, String price) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        // Make sure name isn't empty
        if (name.equals("")) {
            errorMsgs.add(new ErrorMsg("Name cannot be empty!"));
            return errorMsgs;
        }

        // Grab the Orderable from the database, throw error if it doesn't exist
        Orderable orderable = null;
        for (Orderable o: orderableDB.getOrderableList()) {
            if (o.getName().equals(name)) {
                orderable = o;
                break;
            }
        }
        if (orderable == null) {
            errorMsgs.add(new ErrorMsg("Can't update '" + name + "' since it doesn't exist!"));
            return errorMsgs;
        }


        return errorMsgs;
    }

    public List<ErrorMsg> updateShelfLife(String name, String shelfLife) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        // Make sure name isn't empty
        if (name.equals("")) {
            errorMsgs.add(new ErrorMsg("Name cannot be empty!"));
            return errorMsgs;
        }

        // Grab the Orderable from the database, throw error if it doesn't exist
        Orderable orderable = null;
        for (Orderable o: orderableDB.getOrderableList()) {
            if (o.getName().equals(name)) {
                orderable = o;
                break;
            }
        }
        if (orderable == null) {
            errorMsgs.add(new ErrorMsg("Can't update '" + name + "' since it doesn't exist!"));
            return errorMsgs;
        }


        return errorMsgs;
    }

    public List<ErrorMsg> addOrderable(String name, String price, String shelfLife) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        // Make sure name isn't empty
        if (name.equals("")) {
            errorMsgs.add(new ErrorMsg("Name cannot be empty!"));
            return errorMsgs;
        }

        // Validate price
        if (price.equals("")) {
            errorMsgs.add(new ErrorMsg("Price cannot be empty!"));
        }
        PriceValidator pv = new PriceValidator();
        Result<Float, List<ErrorMsg>> priceResult = pv.validate(price);
        if (priceResult.getError() != null) {
            return priceResult.getError();
        }
        Float oPrice = priceResult.getResult();

        // Validate shelf life
        if (price.equals("")) {
            errorMsgs.add(new ErrorMsg("Price cannot be empty!"));
        }
        ShelfLifeValidator sv = new ShelfLifeValidator();
        Result<Integer, List<ErrorMsg>> lifeResult = sv.validate(shelfLife);
        if (lifeResult.getError() != null) {
            return lifeResult.getError();
        }
        Integer oShelfLife = lifeResult.getResult();

        // Throw error if orderable already exists
        Orderable orderable = null;
        for (Orderable o: orderableDB.getOrderableList()) {
            if (o.getName().equals(name)) {
                orderable = o;
                break;
            }
        }
        if (orderable != null) {
            errorMsgs.add(new ErrorMsg("'" + name + "' already exists and can't be added again!"));
            return errorMsgs;
        }

        Orderable newOrderable = new Orderable(name, oPrice, oShelfLife);
        orderableDB.addOrderable(newOrderable);

        return errorMsgs;
    }

    public List<ErrorMsg> removeOrderable(String name) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        // Make sure name isn't empty
        if (name.equals("")) {
            errorMsgs.add(new ErrorMsg("Name cannot be empty!"));
            return errorMsgs;
        }

        // Grab the Orderable from the database, throw error if it doesn't exist
        Orderable orderable = null;
        for (Orderable o: orderableDB.getOrderableList()) {
            if (o.getName().equals(name)) {
                orderable = o;
                break;
            }
        }
        if (orderable == null) {
            errorMsgs.add(new ErrorMsg("Can't remove '" + name + "' since it doesn't exist!"));
            return errorMsgs;
        }

        // Remove it
        orderableDB.removeOrderable(orderable.getName());

        return errorMsgs;
    }

    public String[][] getOrderableList() {
        return TableEntryGenerator.genOrderableTableEntries(orderableDB.getOrderableList());
    }
}
