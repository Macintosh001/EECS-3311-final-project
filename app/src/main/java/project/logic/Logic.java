package project.logic;

import project.objects.ErrorMsg;
import project.objects.Product;
import project.persistence.Database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Logic implements ILogic {
    Database database;

    private static int nextBarcode = 0;

    public Logic(Database database, int nextBarcode) {
        this.database = database;
        Logic.nextBarcode = nextBarcode;
    }

    public List<ErrorMsg> addProduct(String name, String quantity, String price, String expiryDate) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        Integer intQuantity = null;
        Float floatPrice = null;
        Date dateExpiryDate = null;

        // Validate name field
        if (name.equals("")) {
            errorMsgs.add(new ErrorMsg("Product name can't be blank!"));
        } else if (hasItemWithName(name)) {
            errorMsgs.add(new ErrorMsg("A product named " + name + " already exists!"));
            return errorMsgs;
        }

        // Validate the quantity field
        // i.e. the quantity cannot be empty, negative and it must be a whole number.
        if (quantity.equals("")) {
            errorMsgs.add(new ErrorMsg("Quantity cannot be blank!"));
        } else {
            try {
                intQuantity = Integer.parseInt(quantity);
                if (intQuantity < 0) {
                    errorMsgs.add(new ErrorMsg("Quantity cannot be negative!"));
                }
            } catch (NumberFormatException ex) {
                errorMsgs.add(new ErrorMsg("Quantity must be a whole number!"));
            }
        }

        // Checkes whether the Price input is correct
        // i.e. whether is blank, negative or is not a decimal number.
        if (price.equals("")) {
           errorMsgs.add(new ErrorMsg("Price cannot be blank!"));
        } else {
            try {
                floatPrice = Float.parseFloat(price);
                if (floatPrice < 0) {
                    errorMsgs.add(new ErrorMsg("Price cannot be negative!"));
                }
            } catch (NumberFormatException ex) {
                errorMsgs.add(new ErrorMsg("Price must be a decimal number!"));
            }
        }

        // Date and the Expiry date is being implemented here.
        // The right format of the date-input should be displayed for the client.
        // the proper format is: yyyy-mm-dd
        if (expiryDate.equals("")) {
            errorMsgs.add(new ErrorMsg("Expiry date cannot be empty!"));
        } else {
            try {
                dateExpiryDate = new SimpleDateFormat("yyyy-MM-dd").parse(expiryDate);
            } catch (ParseException t) {
                errorMsgs.add(new ErrorMsg("Invalid date. Format is 'yyyy-mm-dd'"));
            }
        }

        if (errorMsgs.isEmpty()) {
            Product product = new Product(Logic.nextBarcode, name, intQuantity, floatPrice, dateExpiryDate);
            Logic.nextBarcode++;
            this.database.addProduct(product);
        }

        return errorMsgs;
}

    public List<ErrorMsg> removeProduct(String barcode) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();
        ProductList productList = database.getProductList();
        boolean hasBarcode = false;

        int barcodeInt;

        // Validate barcode
        if (barcode.equals("")) {
            errorMsgs.add(new ErrorMsg("Barcode cannot be empty!"));
            return errorMsgs;
        }

        try {
            barcodeInt = Integer.parseInt(barcode);
        } catch (Exception e) {
            errorMsgs.add(new ErrorMsg("'" + barcode + "' is not a valid barcode!"));
            return errorMsgs;
        }

        for (Product product: productList.getProductList()) {
            if (product.getBarcode() == barcodeInt) {
                hasBarcode = true;
                break;
            }
        }

        if (hasBarcode) {
            this.database.removeProduct(barcodeInt);
        } else {
            errorMsgs.add(new ErrorMsg("There is no product with barcode: " + barcode));
        }

        return errorMsgs;
    }

    @Override
    public List<ErrorMsg> updateProduct(String barcode, String name, String quantity, String price, String expiryDate) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();
        ProductList productList = database.getProductList();

        int barcodeInt;
        Integer intQuantity = null;
        Float floatPrice = null;
        Date dateExpiryDate = null;

        boolean hasBarcode = false;

        // Validate barcode
        if (barcode.equals("")) {
            errorMsgs.add(new ErrorMsg("Barcode cannot be empty!"));
            return errorMsgs;
        }

        try {
            barcodeInt = Integer.parseInt(barcode);
        } catch (Exception e) {
            errorMsgs.add(new ErrorMsg("'" + barcode + "' is not a valid barcode!"));
            return errorMsgs;
        }

        for (Product product: productList.getProductList()) {
            if (product.getBarcode() == barcodeInt) {
                hasBarcode = true;
                break;
            }
        }

        if (!hasBarcode) {
            errorMsgs.add(new ErrorMsg("There is no product with barcode: " + barcode));
        }

        // Validate the quantity field
        // i.e. cannot be negative and it must be a whole number.
        try {
            intQuantity = Integer.parseInt(quantity);
            if (intQuantity < 0) {
                errorMsgs.add(new ErrorMsg("Quantity cannot be negative!"));
            }
        } catch (NumberFormatException ex) {
            errorMsgs.add(new ErrorMsg("Quantity must be a whole number!"));
        }

        // Checkes whether the Price input is correct
        // i.e. whether is blank, negative or is not a decimal number.
        try {
            floatPrice = Float.parseFloat(price);
            if (floatPrice < 0) {
                errorMsgs.add(new ErrorMsg("Price cannot be negative!"));
            }
        } catch (NumberFormatException ex) {
            errorMsgs.add(new ErrorMsg("Price must be a decimal number!"));
        }

        // Date and the Expiry date is being implemented here.
        // The right format of the date-input should be displayed for the client.
        // the proper format is: yyyy-mm-dd
        try {
            dateExpiryDate = new SimpleDateFormat("yyyy-MM-dd").parse(expiryDate);
        } catch(ParseException t) {
            errorMsgs.add(new ErrorMsg("Invalid date. Format is 'yyyy-mm-dd'"));
        }

        // If there are no errors, then we can go ahead and update the
        // database with the new product
        if (errorMsgs.isEmpty()) {
            Product oldProduct = database.getProduct(barcodeInt);

            // If the enty was empty, then we take the old
            // value for the product that will be replaced in the database
            if (name.equals("")) {
                name = oldProduct.getName();
            }

            if (quantity.equals("")) {
                intQuantity = oldProduct.getQuantity();
            }

            if (price.equals("")) {
                floatPrice = oldProduct.getPrice();
            }

            if (expiryDate.equals("")) {
                dateExpiryDate = oldProduct.getExpityDate();
            }

            database.replaceProduct(new Product(
                    oldProduct.getBarcode(),
                    name,
                    intQuantity,
                    floatPrice,
                    dateExpiryDate
            ));
        }

        return errorMsgs;
    }

    public ProductList getProductList() {
        return this.database.getProductList();
    }

    private boolean hasItemWithName(String name) {
        ProductList productList = this.database.getProductList();
        for (String[] product : productList.getTableEntries()) {
            if (product[1].equals(name)) {
                return true;
            }
        }
        return false;
  }
}
