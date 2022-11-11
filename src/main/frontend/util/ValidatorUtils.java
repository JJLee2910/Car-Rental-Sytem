package main.frontend.util;

import main.frontend.exception.ValidationException;
import main.utils.StringUtils;

/**
 * validation method for entire system
 * 1. validate if its empty
 * 2. validate regex during register/add booking
 * 3. validate if input is integer
 * 4. validate input is currency or not
 */
public class ValidatorUtils {
    public static void validateNotEmpty(String text, String errorMsg) throws ValidationException {
        if (StringUtils.isBlank(text))
            throw new ValidationException(errorMsg);
    }

    public static void validateMatchRegex(String text, String regex, String errorMsg) throws ValidationException {
        if (text == null || !text.matches(regex))
            throw new ValidationException(errorMsg);
    }

    public static void validateIsInteger(String text, String errorMsg) throws ValidationException {
        if (!StringUtils.isInteger(text))
            throw new ValidationException(errorMsg);
    }

    public static void validateIsCurrency(String text, String errorMsg) throws ValidationException {
        validateMatchRegex(text, "(\\$)?[0-9]+\\.*[0-9]{0,2}", errorMsg);
    }
}
