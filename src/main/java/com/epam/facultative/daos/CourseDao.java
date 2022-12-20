package com.epam.facultative.daos;

import com.epam.facultative.entity.Course;
import com.epam.facultative.entity.Status;
import com.epam.facultative.exception.DAOException;

import java.util.List;

public interface CourseDao extends Dao<Course> {
    List<Course> getByUser(int userId, int offset, int numberOfRows) throws DAOException;

    List<Course> getByCategory(int categoryId, int offset, int numberOfRows) throws DAOException;

    List<Course> getByStatus(int id, Status status, int offset, int numberOfRows) throws DAOException;

    int getGrade(int courseId, int userId) throws DAOException;

    void addUserToCourse(int courseId, int userId) throws DAOException;

    void updateUsersCourse(int courseId, int userId, int grade) throws DAOException;

    void addNumberStudentsToCourse(int id) throws DAOException;

    List<Course> getAllPagination(int offset, int numberOfRows) throws DAOException;

    int getNoOfRecords();

    List<Course> getAllSortPagination(int offset, int numberOfRows, String sortBy) throws DAOException;
}
