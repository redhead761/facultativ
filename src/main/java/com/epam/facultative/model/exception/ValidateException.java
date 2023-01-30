package com.epam.facultative.model.exception;

/**
 * Used in case of non-validation of data
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class ValidateException extends Exception {

    public ValidateException() {
        super();
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }

    public ValidateException(String message) {
        super(message);
    }
}
