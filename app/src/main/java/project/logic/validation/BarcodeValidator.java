package project.logic.validation;

import project.objects.ErrorMsg;
import project.objects.Result;

import java.util.ArrayList;
import java.util.List;

public class BarcodeValidator implements Validator<Integer> {
    /**
     * Check if the string is a valid barcode
     * @param entry the badcode
     * @return a result with either errors or the barcode as an int
     */
    @Override
    public Result<Integer, List<ErrorMsg>> validate(String entry) {
        Integer result = null;
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        try {
            result = Integer.parseInt(entry);
        } catch (NumberFormatException ex) {
            errorMsgs.add(new ErrorMsg("Barcode must be a whole number!"));
        }

        if (errorMsgs.isEmpty()) {
            return new Result<>(result, null);
        } else {
            return new Result<>(null, errorMsgs);
        }
    }
}
