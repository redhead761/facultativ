package com.epam.facultative.model.exception;

/**
 * Wrapper for DAOException
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class ServiceException extends Exception {

    public ServiceException() {
        super();
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
