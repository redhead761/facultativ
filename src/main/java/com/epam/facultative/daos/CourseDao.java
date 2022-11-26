package com.epam.facultative.daos;

import com.epam.facultative.entity.Course;
import com.epam.facultative.entity.Status;
import com.epam.facultative.exception.DAOException;

import java.util.List;

public interface CourseDao extends Dao<Course> {
    List<Course> getByUser(int userId) throws DAOException;

    List<Course> getByCategory(int categoryId) throws DAOException;

    void addUserToCourse(int courseId, int userId) throws DAOException;

    void updateUsersCourse(int courseId, int userId, int grade) throws DAOException;

    int getGrade(int courseId, int userId) throws DAOException;

    List<Course> getByStatus(int id, Status status) throws DAOException;
}
