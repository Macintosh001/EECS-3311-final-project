package project.logic.validation;

import project.objects.ErrorMsg;
import project.objects.Result;

import java.util.ArrayList;
import java.util.List;

public class QuantityValidator implements Validator<Integer> {

    @Override
    public Result<Integer, List<ErrorMsg>> validate(String entry) {
        Integer result = null;
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        if (entry.equals("")) {
            errorMsgs.add(new ErrorMsg("Quantity cannot be blank!"));
        } else {
            try {
                result = Integer.parseInt(entry);
                if (result < 0) {
                    errorMsgs.add(new ErrorMsg("Quantity cannot be negative!"));
                }
            } catch (NumberFormatException ex) {
                errorMsgs.add(new ErrorMsg("Quantity must be a whole number!"));
            }
        }

        if (errorMsgs.isEmpty()) {
            return new Result<>(result, null);
        } else {
            return new Result<>(null, errorMsgs);
        }
    }
}
