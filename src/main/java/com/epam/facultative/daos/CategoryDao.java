package com.epam.facultative.daos;

import com.epam.facultative.entities.Category;
import com.epam.facultative.exception.DAOException;

import java.util.List;

public interface CategoryDao extends Dao<Category> {
    List<Category> getAllPagination(int offset, int numberOfRows) throws DAOException;

    int getNoOfRecords();

}
