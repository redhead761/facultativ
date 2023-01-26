package com.epam.facultative.model.dao;

import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.exception.ValidateException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(String param) throws DAOException;

    void add(T t) throws DAOException, ValidateException;

    void update(T t) throws DAOException, ValidateException;

    void delete(int id) throws DAOException, ValidateException;

    Map.Entry<Integer, List<T>> getAll(String param) throws DAOException;
}

