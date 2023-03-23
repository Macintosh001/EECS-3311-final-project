package project.logic;

import project.logic.validation.BarcodeValidator;
import project.objects.ErrorMsg;
import project.objects.Product;
import project.objects.Result;
import project.persistence.ProductDatabase;

import java.util.ArrayList;
import java.util.List;

public class StockManagingLogic extends StockCheckingLogic {
    public StockManagingLogic(ProductDatabase db) {
        super(db);
    }

    /**
     * Simply removed the product from stock
     * @param barcode the barcode of the product being removed
     * @return any errors
     */
    public List<ErrorMsg> removeProduct(String barcode) {
        // validate barcode
        List<ErrorMsg> errorMsgs = new ArrayList<>();
        BarcodeValidator v = new BarcodeValidator();
        Result<Integer, List<ErrorMsg>> barcodeResult = v.validate(barcode);

        if (barcodeResult.getResult() == null) {
            return barcodeResult.getError();
        }

        // check if barcode exists
        Integer oBarcode = barcodeResult.getResult();
        boolean hasBarcode = false;
        for (Product p: super.productDatabase.getProductList()) {
            if (p.getBarcode().equals(oBarcode)) {
                hasBarcode = true;
                break;
            }
        }
        if (!hasBarcode) {
            errorMsgs.add(new ErrorMsg("Product with barcode '" + barcode + "' does not exist!"));
            return errorMsgs;
        }

        // remove it if it does
        super.productDatabase.removeProduct(oBarcode);
        return errorMsgs;
    }
    //remove all products which in the expired list
    public void removeExpiredProducts() {
        String[][] expiredProducts = getExpiredList();
        for (String[] product : expiredProducts) {
            int exBarcode = Integer.parseInt(product[0]);
            productDatabase.removeProduct(exBarcode);
        }
    }
}
