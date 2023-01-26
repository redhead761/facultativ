package com.epam.facultative.model.dao;

import com.epam.facultative.model.entities.User;
import com.epam.facultative.model.exception.DAOException;

import java.io.InputStream;

public interface UserDao extends Dao<User> {
    void addAvatar(int userId, InputStream avatar) throws DAOException;
}
