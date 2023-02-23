package project.logic.validation;

import project.objects.ErrorMsg;
import project.objects.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateValidator implements Validator<Date> {
    @Override
    public Result<Date, List<ErrorMsg>> validate(String entry) {
        Date result = null;
        List<ErrorMsg> errorMsgs = new ArrayList<>();

        try {
            result = new SimpleDateFormat("yyyy-MM-dd").parse(entry);
        } catch (ParseException t) {
            errorMsgs.add(new ErrorMsg("Invalid date. Format is 'yyyy-mm-dd'"));
        }

        if (errorMsgs.isEmpty()) {
            return new Result<>(result, null);
        } else {
            return new Result<>(null, errorMsgs);
        }
    }
}
