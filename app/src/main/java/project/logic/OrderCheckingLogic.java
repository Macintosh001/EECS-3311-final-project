package project.logic;

import project.logic.validation.DateValidator;
import project.logic.validation.PriceValidator;
import project.logic.validation.QuantityValidator;
import project.objects.ErrorMsg;
import project.objects.Result;
import project.persistence.Database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderCheckingLogic {
    private Database db;

    public OrderCheckingLogic(Database db) {
        this.db = db;
    }

    public String[][] getProductList() {
        return TableEntryGenerator.genTableEntries(db.getProductList());
    }

    public Result<String[][], List<ErrorMsg>> getFilteredList(
            String minPrice,
            String maxPrice,
            String minQuantity,
            String maxQuantity,
            String minExpiryDate,
            String maxExpiryDate
    ) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        // Validate price entries
        PriceValidator priceValidator = new PriceValidator();

        Result<Float, List<ErrorMsg>> minPriceResult = priceValidator.validate(minPrice);
        if (minPriceResult.getError() == null) {
            // Yay!
        } else {
            errorMsgs.addAll(minPriceResult.getError());
        }

        Result<Float, List<ErrorMsg>> maxPriceResult = priceValidator.validate(maxPrice);
        if (maxPriceResult.getError() == null) {
            // Yay!
        } else {
            errorMsgs.addAll(maxPriceResult.getError());
        }

        // Validate quantity entries
        QuantityValidator quantityValidator = new QuantityValidator();

        Result<Integer, List<ErrorMsg>> minQuantityResult = quantityValidator.validate(minQuantity);
        if (minQuantityResult.getError() == null) {
            // Yay!
        } else {
            errorMsgs.addAll(minQuantityResult.getError());
        }

        Result<Integer, List<ErrorMsg>> maxQuantityResult = quantityValidator.validate(maxQuantity);
        if (maxQuantityResult.getError() == null) {
            // Yay!
        } else {
            errorMsgs.addAll(maxQuantityResult.getError());
        }

        // Validate quantity entries
        DateValidator dateValidator = new DateValidator();

        Result<Date, List<ErrorMsg>> minDateResult = dateValidator.validate(minExpiryDate);
        if (minDateResult.getError() == null) {
            // Yay!
        } else {
            errorMsgs.addAll(minDateResult.getError());
        }

        Result<Date, List<ErrorMsg>> maxDateResult = dateValidator.validate(maxExpiryDate);
        if (maxDateResult.getError() == null) {
            // Yay!
        } else {
            errorMsgs.addAll(maxDateResult.getError());
        }

        if (errorMsgs.isEmpty()) {
            return new Result<>(null, null); // TODO finish implementation so it actually returns the right thing
        } else {
            return new Result<>(null, errorMsgs);
        }
    }
}
