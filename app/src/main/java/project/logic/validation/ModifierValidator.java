package project.logic.validation;

import project.objects.ErrorMsg;
import project.objects.Result;

import java.util.ArrayList;
import java.util.List;

public class ModifierValidator implements Validator<Float> {
    /**
     * Check if the string is a valid 'percent discount'
     * @param entry the string
     * @return the float corresponding to the discount or errors
     */
    @Override
    public Result<Float, List<ErrorMsg>> validate(String entry) {
        Float result = null;
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        try {
            result = Float.parseFloat(entry);
            if (!(-100.0f <= result && result <= 100.0f)) {
                errorMsgs.add(new ErrorMsg("Sale/markup must be between a -100% and 100% change!"));
            }
        } catch (NumberFormatException ex) {
            errorMsgs.add(new ErrorMsg("Sale/markup must be a decimal number!"));
        }

        if (errorMsgs.isEmpty()) {
            return new Result<>(result, null);
        } else {
            return new Result<>(null, errorMsgs);
        }
    }
}
