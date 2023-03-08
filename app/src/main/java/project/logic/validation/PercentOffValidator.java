package project.logic.validation;

import project.objects.ErrorMsg;
import project.objects.Result;

import java.util.ArrayList;
import java.util.List;

public class PercentOffValidator implements Validator<Float> {
    @Override
    public Result<Float, List<ErrorMsg>> validate(String entry) {
        Float result = null;
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        try {
            result = Float.parseFloat(entry);
            if (!(0.0f <= result && result <= 100.0f)) {
                errorMsgs.add(new ErrorMsg("Discount must be between 0% and 100% off!"));
            }
        } catch (NumberFormatException ex) {
            errorMsgs.add(new ErrorMsg("Discount must be a decimal number!"));
        }

        if (errorMsgs.isEmpty()) {
            return new Result<>(result, null);
        } else {
            return new Result<>(null, errorMsgs);
        }
    }
}
