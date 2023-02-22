package project.logic.validation;

import project.objects.ErrorMsg;
import project.objects.Result;

import java.util.List;

public interface Validator<T> {
    Result<T, List<ErrorMsg>> validate(String entry);
}
