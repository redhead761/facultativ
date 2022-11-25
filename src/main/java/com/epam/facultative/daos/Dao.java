package com.epam.facultative.daos;

import com.epam.facultative.exception.DAOException;

import java.util.List;

public interface Dao<T> {
    List<T> getAll() throws DAOException;

    T getById(int id) throws DAOException;

    T getByName(String name) throws DAOException;

    void add(T t) throws DAOException;

    void update(T t) throws DAOException;

    void delete(int id) throws DAOException;
}

