package com.epam.facultative.model.dao;

import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.exception.ValidateException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * CRUD DAO interface.
 * Implement CRUD methods in all concrete DAOs
 *
 * @param <T> – the type of entities
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public interface Dao<T> {
    /**
     * Obtains instance of entity from database by param
     *
     * @param param - parameter value
     * @return Optional.ofNullable - entity is null if the entity is not found.
     * @throws DAOException is wrapper for SQLException
     */
    Optional<T> get(String param) throws DAOException;

    /**
     * Insert record into database
     *
     * @param t - concrete entity in implementations
     * @throws DAOException is wrapper for SQLException
     */
    void add(T t) throws DAOException, ValidateException;

    /**
     * Updates entity
     *
     * @param t should contain all necessary fields
     * @throws DAOException is wrapper for SQLException
     */
    void update(T t) throws DAOException, ValidateException;

    /**
     * Deletes record in database table
     *
     * @param id - value of id field in database
     * @throws DAOException is wrapper for SQLException
     */
    void delete(int id) throws DAOException, ValidateException;

    /**
     * Obtains list of all entities from database
     *
     * @param param -  parameter value
     * @return entities list
     * @throws DAOException is wrapper for SQLException
     */
    Map.Entry<Integer, List<T>> getAll(String param) throws DAOException;
}

