package com.epam.facultative.data_layer.daos;

import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ValidateException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> getById(int id) throws DAOException;

    Optional<T> getByName(String name) throws DAOException;

    void add(T t) throws DAOException, ValidateException;

    void update(T t) throws DAOException, ValidateException;

    void delete(int id) throws DAOException, ValidateException;

    Map.Entry<Integer, List<T>> getAll(String param) throws DAOException;
}

