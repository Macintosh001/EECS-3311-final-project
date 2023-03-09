package project.logic.validation;

import project.objects.ErrorMsg;
import project.objects.Result;

import java.util.List;

/**
 * This inferface is used to validate string inputs and return, for example, the corresponding integer
 * It is designed to reduce code duplication
 * @param <T>
 */
public interface Validator<T> {
    Result<T, List<ErrorMsg>> validate(String entry);
}