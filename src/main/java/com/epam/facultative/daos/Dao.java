package com.epam.facultative.daos;

import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ValidateException;

import java.util.List;

public interface Dao<T> {
    List<T> getAll() throws DAOException;

    T getById(int id) throws DAOException;

    T getByName(String name) throws DAOException;

    void add(T t) throws DAOException, ValidateException;

    void update(T t) throws DAOException, ValidateException;

    void delete(int id) throws DAOException;

    List<T> getAllPagination(int offset, int numberOfRows) throws DAOException;

    int getNoOfRecords();
}

