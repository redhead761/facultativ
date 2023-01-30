package com.epam.facultative.model.dao;

import com.epam.facultative.model.entities.User;
import com.epam.facultative.model.exception.DAOException;

import java.io.InputStream;

/**
 * User DAO interface.
 * Implement methods due to database type
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public interface UserDao extends Dao<User> {
    /**
     * Inserts a new user avatar to database
     *
     * @param userId, avatar - must be not null
     * @throws DAOException is wrapper for SQLException
     */
    void addAvatar(int userId, InputStream avatar) throws DAOException;
}
