package project.logic;

import project.logic.validation.DateValidator;
import project.logic.validation.PriceValidator;
import project.logic.validation.QuantityValidator;
import project.objects.ErrorMsg;
import project.objects.FilterProduct;
import project.objects.Result;
import project.persistence.ProductDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockCheckingLogic {
    ProductDatabase db;

    public StockCheckingLogic(ProductDatabase db) {
        this.db = db;
    }

    /**
     * Returns a list of all products in the database. It's returns in a format (String[][]) that can be use to
     * directly update a TableModel in the UI.
     * @return The list in a format usable by TableModel.
     */
    public String[][] getProductList() {
        return TableEntryGenerator.genProductTableEntries(db.getProductList());
    }

    /**
     * Returns a list of products in the database that have been filtered. All the parameters are strings taken
     * directly from the UI. A string in the filter can be empty.
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @param minQuantity the minimum quantity
     * @param maxQuantity the maximum quantity
     * @param minExpiryDate the minimum expiry date
     * @param maxExpiryDate the maximum expiry date
     * @return A String[][] containing what is needed to directly update the TableModel in the UI
     */
    public Result<String[][], List<ErrorMsg>> getFilteredList(
            String minPrice,
            String maxPrice,
            String minQuantity,
            String maxQuantity,
            String minExpiryDate,
            String maxExpiryDate
    ) {
        List<ErrorMsg> errorMsgs = new ArrayList<>();
        Float oMinPrice = null;
        Float oMaxPrice = null;
        Integer oMinQuantity = null;
        Integer oMaxQuantity = null;
        Date oMinDate = null;
        Date oMaxDate = null;

        // Validate price entries
        PriceValidator priceValidator = new PriceValidator();

        if (!minPrice.equals("")) {
            Result<Float, List<ErrorMsg>> minPriceResult = priceValidator.validate(minPrice);
            if (minPriceResult.getError() == null) {
                oMinPrice = minPriceResult.getResult();
            } else {
                errorMsgs.addAll(minPriceResult.getError());
            }
        }

        if (!maxPrice.equals("")) {
            Result<Float, List<ErrorMsg>> maxPriceResult = priceValidator.validate(maxPrice);
            if (maxPriceResult.getError() == null) {
                oMaxPrice = maxPriceResult.getResult();
            } else {
                errorMsgs.addAll(maxPriceResult.getError());
            }
        }

        // Validate quantity entries
        QuantityValidator quantityValidator = new QuantityValidator();

        if (!minQuantity.equals("")) {
            Result<Integer, List<ErrorMsg>> minQuantityResult = quantityValidator.validate(minQuantity);
            if (minQuantityResult.getError() == null) {
                oMinQuantity = minQuantityResult.getResult();
            } else {
                errorMsgs.addAll(minQuantityResult.getError());
            }
        }

        if (!maxQuantity.equals("")) {
            Result<Integer, List<ErrorMsg>> maxQuantityResult = quantityValidator.validate(maxQuantity);
            if (maxQuantityResult.getError() == null) {
                oMaxQuantity = maxQuantityResult.getResult();
            } else {
                errorMsgs.addAll(maxQuantityResult.getError());
            }
        }

        // Validate date entries
        DateValidator dateValidator = new DateValidator();

        if (!minExpiryDate.equals("")) {
            Result<Date, List<ErrorMsg>> minDateResult = dateValidator.validate(minExpiryDate);
            if (minDateResult.getError() == null) {
                oMinDate = minDateResult.getResult();
            } else {
                errorMsgs.addAll(minDateResult.getError());
            }
        }

        if (!maxExpiryDate.equals("")) {
            Result<Date, List<ErrorMsg>> maxDateResult = dateValidator.validate(maxExpiryDate);
            if (maxDateResult.getError() == null) {
                oMaxDate = maxDateResult.getResult();
            } else {
                errorMsgs.addAll(maxDateResult.getError());
            }
        }

        if (errorMsgs.isEmpty()) {
            List<FilterProduct> filters = new ArrayList<>();

            if (oMinPrice != null || oMaxPrice != null) {
                filters.add(FilterProduct.FilterProductFactory("price", oMinPrice, oMaxPrice));
            }
            if (oMinQuantity != null || oMaxQuantity != null) {
                filters.add(FilterProduct.FilterProductFactory("quantity", oMinQuantity, oMaxQuantity));
            }
            if (oMinDate != null || oMaxDate != null) {
                filters.add(FilterProduct.FilterProductFactory("expirydate", oMinDate, oMaxDate));
            }

            return new Result<>(TableEntryGenerator.genProductTableEntries(db.getFilteredProductList(filters)), null);
        } else {
            return new Result<>(null, errorMsgs);
        }
    }
    //Listing all expired products
    public String[][] getExpiredList() {
        Date currentDate = new Date();
        List<FilterProduct> filters = new ArrayList<>();
        filters.add(FilterProduct.FilterProductFactory("expirydate", null, currentDate));
        return TableEntryGenerator.genProductTableEntries(db.getFilteredProductList(filters));
    }
}
