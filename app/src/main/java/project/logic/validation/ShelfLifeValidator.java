package project.logic.validation;

import project.objects.ErrorMsg;
import project.objects.Result;

import java.util.ArrayList;
import java.util.List;

public class ShelfLifeValidator implements Validator<Integer> {


    /**
     * checks if the input string is a valid shelf life
     * @param entry the input string
     * @return returns the shelf life as an int or returns errors
     */
    public Result<Integer, List<ErrorMsg>> validate(String entry) {
        Integer result = null;
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        try {
            result = Integer.parseInt(entry);
            if (result < 1) {
                errorMsgs.add(new ErrorMsg("Shelf life must be greater than 1!"));
            }
        } catch (NumberFormatException ex) {
            errorMsgs.add(new ErrorMsg("Shelf life must be a whole number!"));
        }

        if (errorMsgs.isEmpty()) {
            return new Result<>(result, null);
        } else {
            return new Result<>(null, errorMsgs);
        }
    }
}
