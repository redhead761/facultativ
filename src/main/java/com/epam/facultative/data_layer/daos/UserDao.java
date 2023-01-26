package com.epam.facultative.data_layer.daos;

import com.epam.facultative.data_layer.entities.User;
import com.epam.facultative.exception.DAOException;

import java.io.InputStream;

public interface UserDao extends Dao<User> {
    void addAvatar(int userId, InputStream avatar) throws DAOException;
}
