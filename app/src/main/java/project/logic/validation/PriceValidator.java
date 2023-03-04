package project.logic.validation;

import project.objects.ErrorMsg;
import project.objects.Result;

import java.util.ArrayList;
import java.util.List;

public class PriceValidator implements Validator<Float> {
    @Override
    public Result<Float, List<ErrorMsg>> validate(String entry) {
        Float result = null;
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        try {
            result = Float.parseFloat(entry);
            if (result < 0) {
                errorMsgs.add(new ErrorMsg("Price cannot be negative!"));
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
