package com.epam.facultative.model.exception;

/**
 * Wrapper for SQLException
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class DAOException extends Exception {
    public DAOException() {
        super();
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
