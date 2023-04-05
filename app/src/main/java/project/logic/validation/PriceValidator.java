package project.logic.validation;

import project.objects.ErrorMsg;
import project.objects.Result;

import java.util.ArrayList;
import java.util.List;

public class PriceValidator implements Validator<Float> {
    /**
     * Checks if the entered string is a valid price
     * @param entry the string
     * @return the price as a float or some errors
     */
    @Override
    public Result<Float, List<ErrorMsg>> validate(String entry) {
        Float result = null;
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        try {
            result = Float.parseFloat(entry);
            if (result < 0.0F) {
                errorMsgs.add(new ErrorMsg("Price cannot be negative!"));
            } else if (result > 100000.0F) {
                errorMsgs.add(new ErrorMsg("Price cannot be higher than $100'000"));
            }
        } catch (NumberFormatException ex) {
            errorMsgs.add(new ErrorMsg("Price must be a decimal number!"));
        }

        if (errorMsgs.isEmpty()) {
            return new Result<>(result, null);
        } else {
            return new Result<>(null, errorMsgs);
        }
    }
}
