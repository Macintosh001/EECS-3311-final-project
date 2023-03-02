package project.logic;

import project.logic.validation.BarcodeValidator;
import project.objects.ErrorMsg;
import project.objects.Product;
import project.objects.Result;
import project.persistence.Database;

import java.util.ArrayList;
import java.util.List;

public class StockManagingLogic extends StockCheckingLogic {
    public StockManagingLogic(Database db) {
        super(db);
    }

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
        for (Product p: super.db.getProductList()) {
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
        super.db.removeProduct(oBarcode);
        return errorMsgs;
    }
}
