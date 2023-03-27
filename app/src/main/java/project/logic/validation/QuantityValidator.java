package project.logic.validation;

import project.objects.ErrorMsg;
import project.objects.Result;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class QuantityValidator implements Validator<Integer> {

    /**
     * checks if the input string is a valid quantity integer
     * @param entry the input string
     * @return returns the quantity as an int or returns errors
     */
    @Override
    public Result<Integer, List<ErrorMsg>> validate(String entry) {
        Integer result = null;
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        try {
            result = Integer.parseInt(entry);
            if (result < 0) {
                errorMsgs.add(new ErrorMsg("Quantity cannot be negative!"));
            }
        } catch (NumberFormatException ex) {
            if(isTooBig(entry)){
                errorMsgs.add(new ErrorMsg("Input is too large!"));
            }
            else{
                errorMsgs.add(new ErrorMsg("Quantity must be a whole number!"));
            }

        }

        if (errorMsgs.isEmpty()) {
            return new Result<>(result, null);
        } else {
            return new Result<>(null, errorMsgs);
        }
    }

    private boolean isTooBig(String entry){
        try{
            BigInteger entryNumber = new BigInteger(entry);
            BigInteger max = BigInteger.valueOf(Integer.MAX_VALUE);
            return entryNumber.compareTo(max) > 0;
        } catch(NumberFormatException ex){
            return false;
        }
    }

}
