package com.epam.facultative.daos;

import com.epam.facultative.entities.Course;
import com.epam.facultative.entities.Status;
import com.epam.facultative.exception.DAOException;

import java.util.List;

public interface CourseDao extends Dao<Course> {
    List<Course> getByUser(int userId, int offset, int numberOfRows) throws DAOException;

    List<Course> getByCategory(int categoryId, int offset, int numberOfRows) throws DAOException;

    List<Course> getByStatus(int id, Status status, int offset, int numberOfRows) throws DAOException;

    int getNoOfRecords();

    List<Course> getAllSortPagination(int offset, int numberOfRows, String sortBy) throws DAOException;

    int getAmountStudentOnCourse(int courseId) throws DAOException;
}
